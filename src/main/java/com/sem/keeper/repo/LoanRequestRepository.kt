package com.sem.keeper.repo

import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.entity.UserEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface LoanRequestRepository : PagingAndSortingRepository<LoanRequestEntity, Long> {
    fun findByElvinne(elvinne: UserEntity): Set<LoanRequestEntity>
}
