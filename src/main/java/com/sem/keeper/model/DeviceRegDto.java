package com.sem.keeper.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Eszköz hozzáadására és szerkesztésére használt DTO
 */
@Data
public class DeviceRegDto {

    /**
     * Eszköz rövid neve
     */
    @NotEmpty
    private String name;

    /**
     * Eszköz hosszabb leírása
     */
    @NotEmpty
    private String description;

    /**
     * Könnyen leolvasható vonalkód, későbbi használatra
     */
    private String barcode;

    /**
     * USB-s olvasóval beolvasható azonosító, későbbi használatra
     */
    private String rfid;
}
