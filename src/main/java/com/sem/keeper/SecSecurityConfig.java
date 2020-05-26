package com.sem.keeper;

import com.sem.keeper.service.SemAuthSuccessHandler;
import com.sem.keeper.service.SemUserDetailsService;
import com.sem.keeper.service.SemAuthProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SemAuthProvider authenticationProvider;

    private static final Logger log = LoggerFactory.getLogger(SecSecurityConfig.class);

    @Autowired
    private SemUserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        log.info("authprov");
        log.error(authenticationProvider.getClass().toString());
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("MEMBER")
                .antMatchers("/loan/**").hasRole("MEMBER")
                .antMatchers("/device/**").hasRole("MEMBER")
                .antMatchers("/profile/**").hasRole("USER")
                .antMatchers("/").permitAll()
                .and().formLogin().successHandler(authenticationSuccessHandler())
                .and().csrf().disable();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("detserv");
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new SemAuthSuccessHandler();
    }

}
