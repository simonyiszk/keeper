package com.sem.keeper

import com.sem.keeper.service.SemAuthSuccessHandler
import com.sem.keeper.service.SemUserDetailsService
import hu.gerviba.authsch.AuthSchAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import java.lang.Exception


@Configuration
@EnableWebSecurity
open class SecSecurityConfig(private val userDetailsService: SemUserDetailsService) : WebSecurityConfigurerAdapter() {
    @Autowired
    @Throws(Exception::class)
    fun configAuthentication(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
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
            .and().csrf().disable()
    }

    @Bean
    open fun authenticationFailureHandler(): AuthenticationFailureHandler {
        return SimpleUrlAuthenticationFailureHandler()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun authenticationSuccessHandler(): AuthenticationSuccessHandler {
        return SemAuthSuccessHandler()
    }

    @Bean
    @ConfigurationProperties(prefix = "authsch")
    open fun authSchApi(): AuthSchAPI {
        return AuthSchAPI()
    }
}
