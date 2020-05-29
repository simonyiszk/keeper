package com.sem.keeper.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Egy eszköz igénylését reprezentáló entitás. Arra jó, hogy azon userek akik nem adhatnak ki eszközt,
 * fel tudnak adni egy kérelmet, és ezt a tagok egy gombnyomással jóváhagyhatják. Egyes szituációkban így kényelmesebb
 */
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
