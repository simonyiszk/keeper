package com.sem.keeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String firstName;

    private String lastName;

    private boolean kiadhat;

    private String email;

    private String username;

    private String auth_string;

    private String password;

    private String room;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    private boolean valid;

    public String getFullName(){
        return firstName+' '+lastName;
    }

    /*
    @OneToMany(mappedBy = "elvitte",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Collection<LoanEntity> loanEntities;
     */
}
