package com.sem.keeper.service

import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.UserRepository
import hu.gerviba.authsch.struct.CardType
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
open class UserService(private val repository: UserRepository) {
    @Transactional
    open fun updateCard(user: UserEntity, card: CardType) {
        user.cardType = card
    }

    @Transactional
    open fun deleteUser(userEntity: UserEntity) {
        repository.delete(userEntity)
    }
}