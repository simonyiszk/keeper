package com.sem.keeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sem.keeper.service.LoanService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Egy eszköz egyszeri kibérlését reprezentáló entitás
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LoanEntity implements Serializable, Comparable<LoanEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Fehasználó, aki felelősséget vállal érte, amíg kint van
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity elvitte;

    /**
     * Felhasználó, aki kiadta
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity kiadta;

    /**
     * Felhasználó, aki felelősséget vállal, hogy működőképes állapotban vette visza
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity visszavette;

    /**
     * Az eszköz amiről szó van
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private DeviceEntity deviceEntity;

    /**
     * Kikölcsönzés dátuma
     */
    @Column(name = "take_date")
    private LocalDateTime takeDate;

    /**
     * Visszahozatali határidő
     */
    @Column(name = "back_date_planned")
    private LocalDateTime backDatePlanned;

    /**
     * Visszahozatal valódi dátuma
     */
    @Column(name = "back_date_real")
    private LocalDateTime backDateReal;

    private String note;

    /**
     * Megnézi, hogy az user elkésett-e
     * @return True, ha az user elkésett a visszahozatallal
     */
    @JsonIgnore
    public boolean isLate(){
        return LocalDateTime.now().isAfter(backDatePlanned);
    }

    public LoanEntity(DeviceEntity deviceEntity, UserEntity kiad, UserEntity elvitte){
        this.elvitte=elvitte;
        this.deviceEntity=deviceEntity;
        this.kiadta=kiad;
        takeDate=LocalDateTime.now();
        backDatePlanned=takeDate.plusDays(7);
    }

    public LoanEntity(LoanRequestEntity loanRequestEntity, UserEntity kiad){
        elvitte=loanRequestEntity.getElvinne();
        deviceEntity=loanRequestEntity.getDeviceEntity();
        kiadta=kiad;
        takeDate=LocalDateTime.now();
        backDatePlanned=takeDate.plusDays(7);
    }

    @JsonIgnore
    public Duration getLength(){
        return Duration.between(takeDate, Objects.requireNonNullElseGet(backDateReal, LocalDateTime::now));
    }

    @Override
    public int compareTo(@NotNull LoanEntity loanEntity) {
        return takeDate.compareTo(loanEntity.takeDate);
    }
}
