package com.sem.keeper.web;

import com.sem.keeper.entity.LoanRequestEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.LoanRequestRepository;
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
import java.util.List;

@Controller
@RequestMapping("/loanrequest")
public class LoanRequestController {

    @Autowired
    LoanRequestService loanRequestService;

    @Autowired
    LoanRequestRepository loanRequestRepository;

    @GetMapping
    public String list(HttpSession session, Model model){
        UserEntity user = (UserEntity) session.getAttribute("user");
        model.addAttribute("user", user);
        List<LoanRequestEntity> requests = loanRequestRepository.findAll();
        model.addAttribute("requests", requests);
        return "loanreqlist";
    }

    @GetMapping("/accept/{loanreqid}")
    public RedirectView accept(HttpSession session, Model model,
                         @PathVariable("loanreqid") String loanreqid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        LoanRequestEntity toAccept = loanRequestRepository.findById(Long.parseLong(loanreqid));
        if (toAccept != null) {
            loanRequestService.accept(toAccept, user);
        }
        return new RedirectView("/loanrequest");
    }

    @GetMapping("/deny/{loanreqid}")
    public RedirectView deny(HttpSession session, Model model,
                         @PathVariable("loanreqid") String loanreqid){
        loanRequestService.deny(Long.parseLong(loanreqid));
        return new RedirectView("/loanrequest");
    }

}
