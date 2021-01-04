package com.sem.keeper.repo

import com.sem.keeper.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : PagingAndSortingRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findByFullNameContainingIgnoreCase(fullName: String, pageable: Pageable): Page<UserEntity>
    fun findByAuthSchId(authSchId: String): Optional<UserEntity>
}