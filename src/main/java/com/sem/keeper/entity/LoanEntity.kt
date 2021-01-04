package com.sem.keeper.entity

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIdentityReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator
import java.io.Serializable
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class LoanEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @JsonIdentityInfo(generator = PropertyGenerator::class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    var deviceEntity: DeviceEntity? = null,

    @JsonIdentityInfo(generator = PropertyGenerator::class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    var elvitte: UserEntity? = null,

    @JsonIdentityInfo(generator = PropertyGenerator::class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    var kiadta: UserEntity? = null,

    @Column(name = "take_date")
    var takeDate: LocalDateTime? = null,

    @Column(name = "back_date_planned")
    var backDatePlanned: LocalDateTime? = null,

    var note: String? = null,

    @Column(name = "back_date_real")
    var backDateReal: LocalDateTime? = null,

    @JsonIdentityInfo(generator = PropertyGenerator::class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    var visszavette: UserEntity? = null

) : Serializable, Comparable<LoanEntity> {
    @get:JsonIgnore
    val isLate: Boolean
        get() = LocalDateTime.now().isAfter(backDatePlanned)

    constructor(loanRequestEntity: LoanRequestEntity, kiad: UserEntity) : this() {
        elvitte = loanRequestEntity.elvinne
        deviceEntity = loanRequestEntity.deviceEntity
        kiadta = kiad
        LocalDateTime.now().let {
            takeDate = it
            backDatePlanned = it.plusDays(7)
        }
    }

    @get:JsonIgnore
    val length: Duration
        get() = Duration.between(
            takeDate, Objects.requireNonNullElseGet(backDateReal,
                { LocalDateTime.now() })
        )

    override operator fun compareTo(other: LoanEntity): Int {
        return takeDate!!.compareTo(other.takeDate)
    }
}
