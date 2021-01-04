package com.sem.keeper.entity

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIdentityReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator
import hu.gerviba.authsch.struct.CardType
import java.io.Serializable
import javax.persistence.*

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
) : Serializable {

    var authSchId: String? = null
    var fullName: String? = null
    var email: String? = null

    @JsonIgnore
    var password: String? = null

    @JsonIgnore
    var room: String? = null

    @JsonIgnore
    var phoneNo: String? = null

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableList<String>? = null

    @JsonIgnore
    var valid = false

    @JsonIgnore
    var cardType = CardType.DO

    @JsonIdentityInfo(generator = PropertyGenerator::class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "elvitte", cascade = [CascadeType.ALL], orphanRemoval = true)
    var loanEntities: Collection<LoanEntity>? = null
    var note: String? = null
    var isKiadhat: Boolean
        get() = roles!!.contains("ROLE_MEMBER")
        set(value) {
            if (value) {
                roles!!.add("ROLE_MEMBER")
            } else {
                roles!!.remove("ROLE_MEMBER")
            }
        }
}