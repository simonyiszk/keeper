package com.sem.keeper.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.LoanRequestRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.SessionAttribute
import java.util.*

@Controller
class RootController (private val loanRequestRepository: LoanRequestRepository) {
    @GetMapping("/")
    fun root(@SessionAttribute user: Optional<UserEntity>, model: MutableMap<String, Any?>): String {
        user.ifPresent {
            val loans: Collection<LoanRequestEntity> = loanRequestRepository.findByElvinne(it)
            val loansStr = ObjectMapper().writeValueAsString(loans)
            model["loanreqs"] = loansStr
        }
        return "index"
    }
}