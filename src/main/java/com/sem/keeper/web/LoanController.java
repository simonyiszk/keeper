package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.DeviceRepository;
import com.sem.keeper.repo.LoanRepository;
import com.sem.keeper.repo.UserRepository;
import com.sem.keeper.service.DeviceAlreadyOnLoanException;
import com.sem.keeper.service.LoanRequestService;
import com.sem.keeper.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        List<LoanEntity> loans = loanRepository.findByVisszavetteIsNullOrderByTakeDate();
        model.addAttribute("loans", loans);
        return "loanlist";
    }

    @GetMapping("/list/all")
    public String listAll(HttpSession session, Model model){
        Iterable<LoanEntity> loans = loanRepository.findAll();

        model.addAttribute("loans", loans);
        return "loanlistall";
    }

    @GetMapping("/return/{loanid}")
    public String returnLoan(HttpSession session, Model model,
                             @PathVariable("loanid") Long loanid){
        Optional<LoanEntity> loanEntity = loanRepository.findById(loanid);
        model.addAttribute("loanEntity", loanEntity.get());

        return "confirmReturnLoan";
    }

    @PostMapping("/return/{loanid}")
    public RedirectView returnLoan(HttpSession session, Model model,
                                   @PathVariable("loanid") String loanid,
                                   @ModelAttribute("loanEntity") LoanEntity loanEntityNote,
                                   RedirectAttributes redirectAttributes){
        Optional<LoanEntity> loanEntity = loanRepository.findById(Long.parseLong(loanid));
        if(loanEntity.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "A kölcsönzés nem található");
            return new RedirectView("/loan/list");
        }

        UserEntity user = (UserEntity) session.getAttribute("user");
        loanService.visszahoz(loanEntity.get(),user);
        loanEntity.get().setNote(loanEntityNote.getNote());
        loanRepository.save(loanEntity.get());
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
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
        model.addAttribute("device", device);
        model.addAttribute("users",userRepository.findAll());
        //List<LoanEntity> loans = loanRepository.findByDeviceEntityAndVisszavetteIsNullOrderByTakeDate(device);
        return "newloanstep2";
    }

    @GetMapping("/new/{deviceid}/{userid}")
    public String newloanStepThree(HttpSession session,Model model,
                                   @PathVariable("deviceid") String deviceid,
                                   @PathVariable("userid") String userid){
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
        Optional<UserEntity> tarhaUser = userRepository.findById(Long.parseLong(userid));
        model.addAttribute("device", device);
        model.addAttribute("tarhauser", tarhaUser.get());
        return "newloanstep3";
    }

    @PostMapping("/new/{deviceid}/{userid}")
    public RedirectView addNewLoanFinalStep(HttpSession session, Model model,
                                            @PathVariable("deviceid") long deviceid,
                                            @PathVariable("userid") long userid,
                                            RedirectAttributes redirectAttributes,
                                            @RequestParam Optional<String> note){
        UserEntity user = (UserEntity) session.getAttribute("user");
        DeviceEntity device = deviceRepository.findById(deviceid);
        Optional<UserEntity> tarhaUser = userRepository.findById(userid);

        try {
            loanService.newLoan(device,user,tarhaUser.get(), note);
        } catch (DeviceAlreadyOnLoanException ex){
            redirectAttributes.addFlashAttribute("message","Az eszköz már ki van adva :(");
        }
        return new RedirectView("/device/"+device.getId());
    }

    @GetMapping("/request/{deviceid}")
    public String requestDevice(HttpSession session, Model model,
                                @PathVariable("deviceid") String deviceid){
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
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
