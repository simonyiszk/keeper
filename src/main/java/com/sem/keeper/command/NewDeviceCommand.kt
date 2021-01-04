package com.sem.keeper.command

import javax.validation.constraints.NotBlank

data class NewDeviceCommand(
    @field:NotBlank
    var name: String? = null,
    @field:NotBlank
    var description: String? = null,
    var barcode: String? = null,
    var rfid: String? = null
)
