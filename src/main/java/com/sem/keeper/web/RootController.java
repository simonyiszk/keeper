package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.DeviceRepository;
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
import java.util.List;
import java.util.Map;

@Controller
public class RootController {

    @Autowired
    UserRepository users;

    @Autowired
    DeviceRepository devices;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String root(HttpSession session, Map<String, Object> model){
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user != null) {
            Iterable<DeviceEntity> devicelist = devices.findAll(PageRequest.of(0, 10));
            model.put("devices", devicelist);
        }
        model.put("user", user);
        return "index";
    }
}
