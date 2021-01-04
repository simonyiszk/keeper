package com.sem.keeper.service

import com.sem.keeper.entity.DeviceEntity
import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.LoanRequestRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
open class LoanRequestService(
    open val loanRequestRepository: LoanRequestRepository,
    open val loanService: LoanService
) {
    open fun addLoanRequest(user: UserEntity, deviceEntity: DeviceEntity): LoanRequestEntity {
        val neu = LoanRequestEntity(user, deviceEntity, LocalDateTime.now())
        return loanRequestRepository.save(neu)
    }

    open fun accept(loanRequestEntity: LoanRequestEntity, kiad: UserEntity) {
        loanService.fromLoanRequest(loanRequestEntity, kiad)
        loanRequestRepository.delete(loanRequestEntity)
    }

    open fun deny(id: Long) {
        this.loanRequestRepository.deleteById(id)
    }
}