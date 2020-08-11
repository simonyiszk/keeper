package com.sem.keeper.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Egy eszköz igénylését reprezentáló entitás. Arra jó, hogy azon userek akik nem adhatnak ki eszközt,
 * fel tudnak adni egy kérelmet, és ezt a tagok egy gombnyomással jóváhagyhatják. Egyes szituációkban így kényelmesebb
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class LoanRequestEntity {

    @Getter(onMethod = @__({@JsonProperty("id")}))
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter(onMethod = @__({
            @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                property = "id"),
            @JsonIdentityReference(alwaysAsId = true)
    }))
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity elvinne;

    @Getter(onMethod = @__({
            @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                    property = "id"),
            @JsonIdentityReference(alwaysAsId = true)
    }))
    @ManyToOne(fetch = FetchType.LAZY)
    private DeviceEntity deviceEntity;

    private LocalDateTime creationDate;

    public LoanRequestEntity(UserEntity userEntity, DeviceEntity deviceEntity){
        elvinne=userEntity;
        this.deviceEntity=deviceEntity;
    }
}
