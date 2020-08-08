package com.sem.keeper;

import com.sem.keeper.service.SemAuthSuccessHandler;
import com.sem.keeper.service.SemUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
import hu.gerviba.authsch.AuthSchAPI;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    //@Autowired
    //private SemAuthProvider authenticationProvider;

    private static final Logger log = LoggerFactory.getLogger(SecSecurityConfig.class);

    @Autowired
    private SemUserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("MEMBER")
                .antMatchers("/loanrequest/**").hasRole("MEMBER")
                .antMatchers("/loan/request/**").hasRole("USER")
                .antMatchers("/loan/**").hasRole("MEMBER")
                .antMatchers("/device/delete/**").hasRole("MEMBER")
                .antMatchers("/device/edit/**").hasRole("MEMBER")
                .antMatchers("/device/new/**").hasRole("MEMBER")
                .antMatchers("/device/**").hasRole("USER")
                .antMatchers("/profile/**").hasRole("USER")
                .antMatchers("/").permitAll()
                .and().formLogin().loginPage("/authSchLogin")
                .successHandler(authenticationSuccessHandler())
                .and().logout().logoutSuccessUrl("/")
                .and().csrf().disable();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
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


    @Bean
    @ConfigurationProperties(prefix = "authsch")
    public AuthSchAPI authSchApi(){
        return new AuthSchAPI();
    }
}
