package com.sem.keeper.entity

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIdentityReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator
import com.sem.keeper.command.NewDeviceCommand
import java.io.Serializable
import java.time.Duration
import java.util.stream.Collectors
import javax.persistence.*

@Entity
data class DeviceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) : Serializable {

    var name: String? = null
    var description: String? = null
    var barcode: String? = null
    var rfid: String? = null

    @JsonIdentityInfo(generator = PropertyGenerator::class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "deviceEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("takeDate")
    var loanEntities: List<LoanEntity>? = null

    constructor(newDeviceCommand: NewDeviceCommand) : this() {
        name = newDeviceCommand.name
        description = newDeviceCommand.description
        rfid = newDeviceCommand.rfid
        barcode = newDeviceCommand.barcode
    }

    fun copyFromRegDto(newDeviceCommand: NewDeviceCommand) {
        name = newDeviceCommand.name
        description = newDeviceCommand.description
        rfid = newDeviceCommand.rfid
        barcode = newDeviceCommand.barcode
    }

    val totalLoanTime: Duration // TODO handle null
        get() = if (loanEntities!!.isEmpty()) {
            Duration.ZERO
        } else {
            loanEntities!!.stream()
                .map { obj: LoanEntity -> obj.length }
                .reduce(
                    Duration.ZERO
                ) { obj: Duration, duration: Duration? ->
                    obj.plus(duration)
                }
        }

    @get:JsonIgnore
    val isKiadva: Boolean
        get() {
            if (loanEntities!!.isEmpty()) return false
            //kiálasztja az időrendben legkésőbbi kölcsönzést. stream.sorted.findfirst nem megy
            val loanEntity = loanEntities!!.stream().sorted().collect(Collectors.toList())[loanEntities!!.size - 1]
            return loanEntity.backDateReal == null
        }
}
