package com.sem.keeper.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoanEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity elvitte;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity kiadta;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity visszavette;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeviceEntity deviceEntity;

    private java.util.Date take_date;

    private java.util.Date back_date_planned;

    private java.util.Date back_date_real;

}
