package com.sem.keeper.web

import com.sem.keeper.service.command.AcceptLoanRequestCommand
import com.sem.keeper.service.command.DenyLoanRequestCommand
import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.LoanRequestRepository
import com.sem.keeper.service.LoanRequestService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttribute
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView
import java.util.ArrayList
import java.util.function.Consumer

@Controller
@RequestMapping("/loanrequest")
class LoanRequestController(
    private val loanRequestService: LoanRequestService,
    private val loanRequestRepository: LoanRequestRepository
) {
    @GetMapping
    fun list(model: Model): String {
        val requests: MutableList<LoanRequestEntity> = ArrayList()
        loanRequestRepository.findAll().forEach(Consumer { e: LoanRequestEntity ->
            requests.add(e)
        })
        model.addAttribute("requests", requests)
        return "loanreqlist"
    }

    @GetMapping("/accept/{loanreqid}")
    fun accept(
        @SessionAttribute user: UserEntity,
        @PathVariable("loanreqid") loanreqid: String,
        redirectAttributes: RedirectAttributes
    ): RedirectView {
        val toAccept = loanRequestRepository.findById(loanreqid.toLong()).get()
        loanRequestService.accept(AcceptLoanRequestCommand(
            loanRequestEntity = toAccept,
            kiad = user
        ))
        return RedirectView("/loanrequest")
    }

    @GetMapping("/deny/{loanreqid}")
    fun deny(@PathVariable("loanreqid") loanreqid: String): RedirectView {
        loanRequestService.deny(DenyLoanRequestCommand.of(loanreqid.toLong()))
        return RedirectView("/loanrequest")
    }
}