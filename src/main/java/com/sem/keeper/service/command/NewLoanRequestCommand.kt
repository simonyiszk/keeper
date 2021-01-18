package com.sem.keeper.service.command

import com.sem.keeper.entity.DeviceEntity
import com.sem.keeper.entity.UserEntity

data class NewLoanRequestCommand(var user: UserEntity, var deviceEntity: DeviceEntity)
