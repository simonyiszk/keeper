package com.sem.keeper.service.command

import javax.validation.constraints.NotBlank

data class NewDeviceCommand(
    @field:NotBlank
    var name: String? = null,
    @field:NotBlank
    var description: String? = null,
    var barcode: String? = null,
    var rfid: String? = null
)
