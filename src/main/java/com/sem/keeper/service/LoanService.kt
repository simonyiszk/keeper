package com.sem.keeper.service

import com.sem.keeper.entity.LoanEntity
import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.LoanRepository
import com.sem.keeper.service.command.*
import com.sem.keeper.service.result.NewLoanResult
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
open class LoanService(private val loanRepository: LoanRepository) {

    open fun fromLoanRequest(command: AcceptLoanRequestCommand): NewLoanResult {
        return newLoan(
            NewLoanCommand(
                deviceEntity = command.loanRequestEntity.deviceEntity!!,
                kiad = command.kiad,
                kivesz = command.loanRequestEntity.elvinne!!
            )
        )
    }

    @Transactional
    open fun newLoan(command: NewLoanCommand): NewLoanResult {
        if (command.deviceEntity.isKiadva) return NewLoanResult.NewLoanFailure("Device already on loan")
        val toAdd = LoanEntity(
            deviceEntity = command.deviceEntity,
            elvitte = command.kivesz,
            kiadta = command.kiad,
            takeDate = LocalDateTime.now(),
            backDatePlanned = LocalDateTime.now().plusDays(7),
            note = command.note
        )
        return NewLoanResult.NewLoanSuccess(loanRepository.save(toAdd))
    }

    @Transactional
    open fun hosszabbit(command: ExtendLoanCommand) {
        val loanEntityPersisted = loanRepository.save(command.loanEntity)
        loanEntityPersisted.backDatePlanned = LocalDateTime.now().plusDays(7)
    }

    @Transactional
    open fun visszahoz(command: TakeBackLoanCommand) {
        val loanEntityPersisted = loanRepository.save(command.loanEntity)
        loanEntityPersisted.backDateReal = LocalDateTime.now()
        loanEntityPersisted.visszavette = command.backUser
    }

    @Transactional
    open fun editNote(command: ChangeLoanNoteCommand): Boolean {
        command.loanEntity.note = command.note
        return true
    }
}