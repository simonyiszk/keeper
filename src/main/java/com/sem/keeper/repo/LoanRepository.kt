package com.sem.keeper.repo

import com.sem.keeper.entity.DeviceEntity
import com.sem.keeper.entity.LoanEntity
import com.sem.keeper.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface LoanRepository : PagingAndSortingRepository<LoanEntity, Long> {
    fun findByElvitte(elvitte: UserEntity): List<LoanEntity>
    fun findByElvitte(elvitte: UserEntity, pageRequest: Pageable): Page<LoanEntity>
    fun findByVisszavetteIsNullOrderByTakeDate(): List<LoanEntity>
    fun findFirstByDeviceEntityOrderByTakeDateDesc(deviceEntity: DeviceEntity): LoanEntity?
    fun findByVisszavetteIsNullOrderByTakeDate(pageable: Pageable): Page<LoanEntity>
}