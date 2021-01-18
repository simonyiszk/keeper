package com.sem.keeper.service.command

import com.sem.keeper.entity.DeviceEntity

data class DeleteDeviceCommand(var deviceToDelete: DeviceEntity)
