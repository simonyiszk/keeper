package com.sem.keeper.service

import com.sem.keeper.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class SemAuthSuccessHandler : AuthenticationSuccessHandler {

    @field:Autowired
    private lateinit var userRepository: UserRepository

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val name = authentication.name
        val user = userRepository.findByEmail(name)
        val session = request.session
        session.setAttribute("user", user)
        response.status = HttpServletResponse.SC_OK
        response.sendRedirect("/")
    }
}