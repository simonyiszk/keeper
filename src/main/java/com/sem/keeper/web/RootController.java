package com.sem.keeper.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.LoanRequestEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.DeviceRepository;
import com.sem.keeper.repo.LoanRepository;
import com.sem.keeper.repo.LoanRequestRepository;
import com.sem.keeper.repo.UserRepository;
import com.sem.keeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class RootController {

    @Autowired
    UserRepository users;

    @Autowired
    DeviceRepository devices;

    @Autowired
    UserService userService;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    LoanRequestRepository loanRequestRepository;

    @GetMapping("/")
    public String root(HttpSession session, Map<String, Object> model){
        UserEntity user = (UserEntity) session.getAttribute("user");

        Collection<LoanRequestEntity> loans = loanRequestRepository.findByElvinne(user);
        ObjectMapper objectMapper = new ObjectMapper();
        String loansStr;
        try {
            loansStr = objectMapper.writeValueAsString(loans);
        } catch (Exception e){
            loansStr = "[]";
        }
        model.put("loanreqs", loansStr);
        return "index";
    }
}
