package com.sem.keeper.web;

import com.sem.keeper.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private static UserRepository userRepository;

    @GetMapping
    public String profileRoot(HttpServletRequest request, Model model){

        HttpSession currentSession = request.getSession();

        System.out.println(currentSession.getAttribute("user"));

        model.addAttribute("user", currentSession.getAttribute("user"));
        System.out.println(model.getAttribute("user"));
        return "profile";
    }
}
