package com.sem.keeper.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class LoanRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity elvinne;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeviceEntity deviceEntity;

    public LoanRequestEntity(UserEntity userEntity, DeviceEntity deviceEntity){
        elvinne=userEntity;
        this.deviceEntity=deviceEntity;
    }
}
