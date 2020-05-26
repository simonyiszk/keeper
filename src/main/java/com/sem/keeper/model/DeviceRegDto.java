package com.sem.keeper.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeviceRegDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private String barcode;

    private String rfid;
}
