package com.sem.keeper.service.command

import com.sem.keeper.entity.UserEntity
import hu.gerviba.authsch.struct.CardType

data class UpdateCardCommand(val user: UserEntity, val card: CardType)
