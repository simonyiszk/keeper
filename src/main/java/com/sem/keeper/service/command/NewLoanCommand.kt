package com.sem.keeper.service.command

import com.sem.keeper.entity.DeviceEntity
import com.sem.keeper.entity.UserEntity
import java.util.*

data class NewLoanCommand(
    val deviceEntity: DeviceEntity,
    val kiad: UserEntity,
    val kivesz: UserEntity,
    val note: String? = null
)
