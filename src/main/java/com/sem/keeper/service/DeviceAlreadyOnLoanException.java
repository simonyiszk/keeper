package com.sem.keeper.service;

public class DeviceAlreadyOnLoanException extends Exception {
    public DeviceAlreadyOnLoanException(String errorMessage){
        super(errorMessage);
    }
}
