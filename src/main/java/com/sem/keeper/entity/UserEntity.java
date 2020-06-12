package com.sem.keeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor()
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
    private String auth_string;

    /**
     * Felhasználó jelszava, bcrypt hash formájában
     */
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
    private boolean valid;

    /**
     * Kényelmi függvény, hogy ne kelljen annyit írni a template-ekben
     * @return
     */
    public String getFullName(){
        return firstName+' '+lastName;
    }

    /*
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "elvitte",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Collection<LoanEntity> loanEntities;
*/
}
