package com.sem.keeper.entity;

import com.fasterxml.jackson.annotation.*;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Felhasználó vezetékneve
     */
    private String firstName;

    /**
     * Felhasználó keresztneve
     */
    private String lastName;

    /**
     * A felhasználó kiadhat-e eszközöket
     */
    private boolean kiadhat;

    /**
     * A felhasznéáló e-mailcíme
     */
    private String email;

    /**
     * A felhasználónév, későbbi felhasználásra
     */
    private String username;

    /**
     * Debug célokra ide néha beírtam cleartextben a jelszót, élesben nem lesz benne
     */
    @JsonIgnore
    private String auth_string;

    /**
     * Felhasználó jelszava, bcrypt hash formájában
     */
    @JsonIgnore
    private String password;

    /**
     * Felhasználó szobája. String kell, mert VPK-ban betűk is vannak benne
     */
    private String room;

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

    /**
     * Kényelmi függvény, hogy ne kelljen annyit írni a template-ekben
     * @return
     */
    @JsonIgnore
    public String getFullName(){
        return firstName+' '+lastName;
    }


    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "elvitte",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Collection<LoanEntity> loanEntities;
}
