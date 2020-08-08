package com.sem.keeper.entity;

import com.fasterxml.jackson.annotation.*;
import hu.gerviba.authsch.struct.CardType;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String authSchId;

    /**
     * Felhasználó vezetékneve
     */
    private String fullName;

    /**
     * A felhasznéáló e-mailcíme
     */
    private String email;

    /**
     * Felhasználó jelszava, bcrypt hash formájában
     */
    @JsonIgnore
    private String password;

    /**
     * Felhasználó szobája. String kell, mert VPK-ban betűk is vannak benne
     */
    private String room;

    private String phoneNo;

    /**
     * Role-ok a spring security számára
     */
    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    /**
     * Érvényes-e az account, későbbi felhasználásra
     */
    @JsonIgnore
    private boolean valid;

    @JsonIgnore
    private CardType cardType = CardType.DO;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "elvitte",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Collection<LoanEntity> loanEntities;

    private String note;

    public boolean isKiadhat(){
        return roles.contains("ROLE_MEMBER");
    }

    public void setKiadhat(boolean value){
        if (value){
            roles.add("ROLE_MEMBER");
        } else {
            roles.remove("ROLE_MEMBER");
        }
    }
}
