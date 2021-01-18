package com.sem.keeper.service

import com.sem.keeper.service.command.*
import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.repo.LoanRequestRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
open class LoanRequestService(
    open val loanRequestRepository: LoanRequestRepository,
    open val loanService: LoanService
) {
    @Transactional
    open fun addLoanRequest(command: NewLoanRequestCommand): LoanRequestEntity {
        val request = LoanRequestEntity(
            elvinne = command.user,
            deviceEntity = command.deviceEntity,
            creationDate = LocalDateTime.now()
        )
        return loanRequestRepository.save(request)
    }

    @Transactional
    open fun accept(command: AcceptLoanRequestCommand) {
        loanService.fromLoanRequest(command)
        loanRequestRepository.delete(command.loanRequestEntity)
    }

    @Transactional
    open fun deny(command: DenyLoanRequestCommand){
        when (command) {
            is DenyLoanRequestCommand.DenyLoanRequestByIdCommand -> loanRequestRepository.deleteById(command.id)
            is DenyLoanRequestCommand.DenyLoanRequestByEntityCommand -> loanRequestRepository.delete(command.loanRequestEntity)
        }
    }
}