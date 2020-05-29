package com.sem.keeper.web;

import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.model.UserRegDto;
import com.sem.keeper.service.UserAlreadyExistException;
import com.sem.keeper.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService users;

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserRegDto userRegDto = new UserRegDto();
        model.addAttribute("userreg", userRegDto);
        return "registration";
    }

    @PostMapping("/registration")
    public RedirectView registerUserAccount(
            @ModelAttribute("userreg") @Valid UserRegDto userRegDto,
            HttpServletRequest request, Errors errors, RedirectAttributes redirectAttributes) {
        try {
            users.registerNewUserAccount(userRegDto);
        } catch (UserAlreadyExistException uaeEx) {

            redirectAttributes.addFlashAttribute("message", "An account for that email already exists.");
            return new RedirectView("/");
        }

        return new RedirectView("/");
    }
}
