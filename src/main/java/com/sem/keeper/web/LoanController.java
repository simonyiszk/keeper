package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.DeviceRepository;
import com.sem.keeper.repo.LoanRepository;
import com.sem.keeper.repo.UserRepository;
import com.sem.keeper.service.LoanRequestService;
import com.sem.keeper.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoanService loanService;

    @Autowired
    LoanRequestService loanRequestService;

    @GetMapping("/list")
    public String list(HttpSession session, Model model){
        UserEntity user = (UserEntity) session.getAttribute("user");
        List<LoanEntity> loans = loanRepository.findByVisszavetteIsNullOrderByTakeDate();
        model.addAttribute("loans", loans);
        model.addAttribute("user", user);
        return "loanlist";
    }

    @GetMapping("/list/all")
    public String listAll(HttpSession session, Model model){
        UserEntity user = (UserEntity) session.getAttribute("user");
        List<LoanEntity> loans  = StreamSupport.stream(loanRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());

        model.addAttribute("loans", loans);
        model.addAttribute("user", user);
        return "loanlistall";
    }

    @GetMapping("/return/{loanid}")
    public RedirectView returnLoan(HttpSession session, Model model,
                             @PathVariable("loanid") String loanid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        Optional<LoanEntity> loanEntity = loanRepository.findById(Long.parseLong(loanid));
        loanService.visszahoz(loanEntity.get(), user);
        return new RedirectView("/loan/list");
    }

    @GetMapping("/extend/{loanid}")
    public RedirectView extendLoan(HttpSession session, Model model,
                             @PathVariable("loanid") String loanid){
        Optional<LoanEntity> loanEntity = loanRepository.findById(Long.parseLong(loanid));
        loanService.hosszabbit(loanEntity.get());
        return new RedirectView("/loan/list");
    }

    @GetMapping("/new/{deviceid}")
    public String newloanStepTwo(HttpSession session, Model model, @PathVariable("deviceid") String deviceid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
        model.addAttribute("device", device);
        model.addAttribute("user", user);
        model.addAttribute("users",userRepository.findAll());
        //List<LoanEntity> loans = loanRepository.findByDeviceEntityAndVisszavetteIsNullOrderByTakeDate(device);
        return "newloanstep2";
    }

    @GetMapping("/new/{deviceid}/{userid}")
    public String newloanStepThree(HttpSession session,Model model,
                                   @PathVariable("deviceid") String deviceid,
                                   @PathVariable("userid") String userid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
        Optional<UserEntity> tarhaUser = userRepository.findById(Long.parseLong(userid));
        model.addAttribute("device", device);
        model.addAttribute("user", user);
        model.addAttribute("tarhauser", tarhaUser.get());
        return "newloanstep3";
    }

    @GetMapping("/new/{deviceid}/{userid}/ok")
    public RedirectView addNewLoanFinalStep(HttpSession session, Model model,
                                            @PathVariable("deviceid") String deviceid,
                                            @PathVariable("userid") String userid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
        Optional<UserEntity> tarhaUser = userRepository.findById(Long.parseLong(userid));
        loanService.newLoan(device,user,tarhaUser.get());
        return new RedirectView("/loan/list");
    }

    @GetMapping("/request/{deviceid}")
    public String requestDevice(HttpSession session, Model model,
                                @PathVariable("deviceid") String deviceid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
        model.addAttribute("user",user);
        model.addAttribute("device",device);
        return "confirmrequest";
    }

    @GetMapping("/request/{deviceid}/ok")
    public RedirectView requestDeviceOk(HttpSession session, Model model,
                                       @PathVariable("deviceid") String deviceid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
        loanRequestService.addLoanRequest(user, device);
        return new RedirectView("/");
    }
}
