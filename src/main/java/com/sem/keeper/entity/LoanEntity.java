package com.sem.keeper.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
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

    @Column(name = "take_date")
    private LocalDateTime takeDate;

    @Column(name = "back_date_planned")
    private LocalDateTime backDatePlanned;

    @Column(name = "back_date_real")
    private LocalDateTime backDateReal;

}
