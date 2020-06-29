package com.sem.keeper.web;

import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.LoanRepository;
import com.sem.keeper.repo.UserRepository;
import com.sem.keeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("")
    public String profileRoot(HttpSession session, Model model){

        UserEntity user = (UserEntity) session.getAttribute("user");
        List<LoanEntity> loans = loanRepository.findByElvitte(user);
        //List<LoanEntity> loans = null;
        model.addAttribute("loans", loans);
        return "profile";
    }
}
