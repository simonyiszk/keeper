package com.sem.keeper.service;

import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SemAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserService userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String name = authentication.getName();
        System.out.println(name);
        System.out.println(userRepo);
        UserEntity user = userRepo.findByEmail(name);

        HttpSession session = request.getSession();
        session.setAttribute("user",user);

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");
    }
}
