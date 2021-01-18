package com.sem.keeper.service

import com.sem.keeper.service.command.DeleteDeviceCommand
import com.sem.keeper.service.command.NewDeviceCommand
import com.sem.keeper.entity.DeviceEntity
import com.sem.keeper.repo.DeviceRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class DeviceService(
    open val repo: DeviceRepository
) {
    fun addDevice(deviceRegDto: NewDeviceCommand): DeviceEntity {
        val toAdd = DeviceEntity(deviceRegDto)
        return repo.save(toAdd)
    }

    fun deleteDevice(command: DeleteDeviceCommand) {
        repo.delete(command.deviceToDelete)
    }
}