package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.DeviceRepository;
import com.sem.keeper.repo.LoanRepository;
import com.sem.keeper.repo.UserRepository;
import com.sem.keeper.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/new")
    public String newloanStepOne(HttpSession session, Model model){
        UserEntity user = (UserEntity) session.getAttribute("user");
        Iterable<DeviceEntity> devicelist = deviceRepository.findAll();
        model.addAttribute("devices", devicelist);
        model.addAttribute("user", user);
        return "newloanstep1";
    }

    @GetMapping("/new/{deviceid}")
    public String newloanStepTwo(HttpSession session, Model model, @PathVariable("deviceid") String deviceid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
        model.addAttribute("device", device);
        model.addAttribute("user", user);
        model.addAttribute("users",userRepository.findAll());
        List<LoanEntity> loans = loanRepository.findByDeviceEntityAndVisszavetteIsNullOrderByTakeDate(device);
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
    public String addNewLoanFinalStep(HttpSession session,Model model,
                                      @PathVariable("deviceid") String deviceid,
                                      @PathVariable("userid") String userid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));
        Optional<UserEntity> tarhaUser = userRepository.findById(Long.parseLong(userid));
        loanService.newLoan(device,user,tarhaUser.get());
    }
}
