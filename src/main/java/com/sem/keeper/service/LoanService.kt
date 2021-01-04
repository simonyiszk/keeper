package com.sem.keeper.service

import com.sem.keeper.entity.DeviceEntity
import com.sem.keeper.entity.LoanEntity
import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.LoanRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Service
open class LoanService(private val loanRepository: LoanRepository) {
    @Throws(DeviceAlreadyOnLoanException::class)
    open fun fromLoanRequest(loanRequestEntity: LoanRequestEntity, kiad: UserEntity): LoanEntity {
        if (loanRequestEntity.deviceEntity!!.isKiadva) {
            throw DeviceAlreadyOnLoanException("Device is already on loan")
        }
        val neu = LoanEntity(loanRequestEntity, kiad)
        return loanRepository.save(neu)
    }

    @Throws(DeviceAlreadyOnLoanException::class)
    @Transactional
    open fun newLoan(
        deviceEntity: DeviceEntity,
        kiad: UserEntity,
        kivesz: UserEntity,
        note: Optional<String>
    ): LoanEntity {
        if (deviceEntity.isKiadva) throw DeviceAlreadyOnLoanException("F")
        val toAdd = LoanEntity(
            deviceEntity = deviceEntity,
            elvitte = kivesz,
            kiadta = kiad,
            takeDate = LocalDateTime.now(),
            backDatePlanned = LocalDateTime.now().plusDays(7),
            note = note.orElse(null)
        )
        return loanRepository.save(toAdd)
    }

    @Transactional
    open fun hosszabbit(loanEntity: LoanEntity) {
        loanEntity.backDatePlanned = LocalDateTime.now().plusDays(7)
    }

    @Transactional
    open fun visszahoz(loanEntity: LoanEntity, backUser: UserEntity?) {
        loanEntity.backDateReal = LocalDateTime.now()
        loanEntity.visszavette = backUser
    }

    @Transactional
    open fun editNote(loanEntity: LoanEntity, note: String?): Boolean {
        loanEntity.note = note
        return true
    }
}