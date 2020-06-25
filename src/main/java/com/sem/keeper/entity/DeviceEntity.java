package com.sem.keeper.entity;

import com.fasterxml.jackson.annotation.*;
import com.sem.keeper.model.DeviceRegDto;
import com.sem.keeper.repo.LoanRequestRepository;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A kikölcsönözhető eszközöket reprezentáló entitás
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Eszköz rövid neve
     */
    private String name;

    /**
     * Eszköz hosszabb leírása
     */
    private String description;

    /**
     * Könnyen leolvasható vonalkód, későbbi használatra
     */
    private String barcode;

    /**
     * USB-s olvasóval beolvasható azonosító, későbbi használatra
     */
    private String rfid;

    public DeviceEntity(String name, String description){
        this.name = name;
        this.description = description;
    }

    /**
     * Azok a kölcsönzések, amikben ezt vették ki.
     * Belső használatra
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "deviceEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("takeDate")
    private List<LoanEntity> loanEntities;

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + description + '\'' +
                '}';
    }

    /**
     * Új eszköz DTO-jából generálás
     * @param deviceRegDto A másolandó DTO
     */
    public DeviceEntity(DeviceRegDto deviceRegDto){
        this.name=deviceRegDto.getName();
        this.description=deviceRegDto.getDescription();
        this.rfid=deviceRegDto.getRfid();
        this.barcode=deviceRegDto.getBarcode();
    }

    /**
     * Új eszköz DTO-jából másolás, editáláshoz szükséges
     * @param deviceRegDto A másolandó DTO
     */
    public void copyFromRegDto(DeviceRegDto deviceRegDto){
        this.name=deviceRegDto.getName();
        this.description=deviceRegDto.getDescription();
        this.rfid=deviceRegDto.getRfid();
        this.barcode=deviceRegDto.getBarcode();
    }

    public Duration getTotalLoanTime(){
        if (loanEntities.size()==0){
            return Duration.ZERO;
        } else {
            return loanEntities.stream()
                    .map(LoanEntity::getLength)
                    .reduce(Duration.ZERO,Duration::plus);
        }
    }

    @JsonIgnore
    public boolean isKiadva(){
        if (loanEntities.size()==0) return false;
        //kiálasztja az időrendben legkésőbbi kölcsönzést. stream.sorted.findfirst nem megy
        LoanEntity loanEntity = loanEntities.stream().sorted().collect(Collectors.toList()).get(loanEntities.size()-1);
        return loanEntity.getBackDateReal() == null;
    }

}

