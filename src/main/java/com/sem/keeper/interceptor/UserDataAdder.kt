package com.sem.keeper.interceptor

import com.sem.keeper.repo.LoanRequestRepository
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UserDataAdder(val loanRequestRepository: LoanRequestRepository) : HandlerInterceptor {
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView
    ) {
        modelAndView.addObject("loanReqCnt", 4)
    }
}
