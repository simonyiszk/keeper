package com.sem.keeper.entity;

import com.sem.keeper.service.LoanService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Egy eszköz egyszeri kibérlését reprezentáló entitás
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LoanEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Fehasználó, aki felelősséget vállal érte, amíg kint van
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity elvitte;

    /**
     * Felhasználó, aki kiadta
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity kiadta;

    /**
     * Felhasználó, aki felelősséget vállal, hogy működőképes állapotban vette visza
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity visszavette;

    /**
     * Az eszköz amiről szó van
     */
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

    /**
     * Megnézi, hogy az user elkésett-e
     * @return True, ha az user elkésett a visszahozatallal
     */
    public boolean isLate(){
        return LocalDateTime.now().isAfter(backDatePlanned);
    }

    public LoanEntity(LoanRequestEntity loanRequestEntity, UserEntity kiad){
        elvitte=loanRequestEntity.getElvinne();
        deviceEntity=loanRequestEntity.getDeviceEntity();
        kiadta=kiad;
        takeDate=LocalDateTime.now();
        backDatePlanned=takeDate.plusDays(7);
    }

}
