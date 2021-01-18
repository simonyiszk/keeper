package com.sem.keeper.service

import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.UserRepository
import com.sem.keeper.service.command.DeleteUserCommand
import com.sem.keeper.service.command.UpdateCardCommand
import hu.gerviba.authsch.struct.CardType
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
open class UserService(private val repository: UserRepository) {
    @Transactional
    open fun updateCard(command: UpdateCardCommand) {
        val user = repository.save(command.user)
        user.cardType = command.card
    }

    @Transactional
    open fun deleteUser(userEntity: UserEntity) {
        repository.delete(userEntity)
    }

    @Transactional
    open fun deleteUser(command: DeleteUserCommand) {
        repository.delete(command.userEntity)
    }
}