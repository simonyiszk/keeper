package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.DeviceRepository;
import com.sem.keeper.repo.LoanRepository;
import com.sem.keeper.repo.UserRepository;
import com.sem.keeper.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    UserRepository users;

    @Autowired
    UserService userService;

    @GetMapping("/makemember")
    public RedirectView makeMember(@RequestParam String useremail){
        UserEntity user = userService.findByEmail(useremail);
        if(user != null){
            userService.makeMember(user);
        }
        return new RedirectView("/admin/users");
    }

    @GetMapping("/unmakemember")
    public RedirectView unMakeMember(@RequestParam String useremail){
        UserEntity user = userService.findByEmail(useremail);
        if(user != null){
            userService.unMakeMember(user);
        }
        return new RedirectView("/admin/users");
    }

    @GetMapping("/deleteuser")
    public RedirectView deleteuser(@RequestParam String useremail){
        UserEntity user = userService.findByEmail(useremail);
        if(user != null){
            userService.deleteUser(user);
        }
        return new RedirectView("/admin/users");
    }

    @GetMapping("/users")
    public String listUsers(HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("users", users.findAll());
        return "userlist";
    }

    @GetMapping("")
    public String root(Model model, HttpSession session){
        //
        // HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        model.addAttribute("user",user);
        return "admin";
    }

}
