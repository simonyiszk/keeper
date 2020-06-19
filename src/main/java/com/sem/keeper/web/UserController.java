package com.sem.keeper.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.LoanRequestEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.model.UserRegDto;
import com.sem.keeper.repo.LoanRepository;
import com.sem.keeper.repo.LoanRequestRepository;
import com.sem.keeper.repo.UserRepository;
import com.sem.keeper.service.UserAlreadyExistException;
import com.sem.keeper.service.UserService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService users;

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @GetMapping("/me")
    public UserEntity me(HttpSession httpSession){
        UserEntity user = (UserEntity) httpSession.getAttribute("user");
        if (user == null)
            return new UserEntity();
        user = userRepository.save(user);
        return user;
    }

    @ResponseBody
    @GetMapping("/myRequests")
    public Set<LoanRequestEntity> myRequests(HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");

        return loanRequestRepository.findByElvinne(user);

    }

    @ResponseBody
    @GetMapping("/myRequestIds")
    public Collection<Long> myRequestIds(HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");

        Set<LoanRequestEntity> set = loanRequestRepository.findByElvinne(user);
        List<Long> res = new ArrayList<>();
        set.forEach(it->{
            res.add(it.getDeviceEntity().getId());
        });
        return res;

    }

    @ResponseBody
    @GetMapping("/myLoans")
    public List<LoanEntity> myLoans(HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");

        List<LoanEntity> loanRequestEntities = loanRepository.findByElvitte(user);
        return loanRequestEntities;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserRegDto userRegDto = new UserRegDto();
        model.addAttribute("userreg", userRegDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(
            @ModelAttribute("userreg") @Valid UserRegDto userRegDto,
            BindingResult bindingResult,
        RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "registration";
        }
        try {
            users.registerNewUserAccount(userRegDto);
        } catch (UserAlreadyExistException uaeEx) {

            redirectAttributes.addFlashAttribute("message", "An account for that email already exists.");
            return "redirect:/";
        }

        return "redirect:/";
    }

    @GetMapping("/editnote/{userid}")
    public String editnote(Model model, HttpSession session, @PathVariable("userid") Long userid){
        UserEntity user = (UserEntity) session.getAttribute("user");
        Optional<UserEntity> toEdit = userRepository.findById(userid);
        if (toEdit.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("toEdit",toEdit.get());
        model.addAttribute("user",user);
        model.addAttribute("backUrl","/user/editnote/"+toEdit.get().getId());
        return "noteedit";
    }

    @PostMapping("/editnote/{userid}")
    public String editnotesave(@ModelAttribute("toEdit") UserEntity toEdit,
                               BindingResult bindingResult,
                               @PathVariable("userid") Long userid){
        Optional<UserEntity> toEditDb = userRepository.findById(userid);
        if (toEditDb.isEmpty()){
            return "redirect:/";
        }
        UserEntity toEditValid = toEditDb.get();
        toEditValid.setNote(toEdit.getNote());
        userRepository.save(toEditValid);
        return "redirect:/admin/users";
    }
}
