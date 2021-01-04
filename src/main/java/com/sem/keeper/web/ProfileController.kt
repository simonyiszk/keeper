package com.sem.keeper.web

import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.LoanRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttribute

@Controller
@RequestMapping("/profile")
class ProfileController (
    private val loanRepository: LoanRepository
) {
    @GetMapping("")
    fun profileRoot(@SessionAttribute(required = false) user: UserEntity, model: Model): String {
        val loans = loanRepository.findByElvitte(user)
        model.addAttribute("loans", loans)
        return "profile"
    }
}