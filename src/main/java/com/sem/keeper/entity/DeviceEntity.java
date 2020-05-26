package com.sem.keeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sem.keeper.model.DeviceRegDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String barcode;

    private String rfid;

    public DeviceEntity(String name, String description){
        this.name = name;
        this.description = description;
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "deviceEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Collection<LoanEntity> loanEntities;

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + description + '\'' +
                '}';
    }

    public DeviceEntity(DeviceRegDto deviceRegDto){
        this.name=deviceRegDto.getName();
        this.description=deviceRegDto.getDescription();
        this.rfid=deviceRegDto.getRfid();
        this.barcode=deviceRegDto.getBarcode();
    }

    public void copyFromRegDto(DeviceRegDto deviceRegDto){
        this.name=deviceRegDto.getName();
        this.description=deviceRegDto.getDescription();
        this.rfid=deviceRegDto.getRfid();
        this.barcode=deviceRegDto.getBarcode();
    }

}

