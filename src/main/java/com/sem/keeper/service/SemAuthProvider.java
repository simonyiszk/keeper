package com.sem.keeper.service;

import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SemAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository repo;

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(SemAuthProvider.class);

    private static PasswordEncoder passwordEncoder;

    public SemAuthProvider(){
        //log.info("I exists");
        log.error("I exists");
    }

    @Autowired
    public SemAuthProvider(PasswordEncoder pe){
        passwordEncoder=pe;
        log.error("I exists 2");
        log.info(pe.getClass().toString());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity Lajoska = repo.findByEmail(name);

        if (passwordEncoder.matches(password,Lajoska.getPassword())) {
            log.info("login "+Lajoska.getEmail());
            ArrayList <String> roles = new ArrayList<>();
            roles.add("USER");
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        } else {
            log.info("login fail "+Lajoska.getEmail());
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
