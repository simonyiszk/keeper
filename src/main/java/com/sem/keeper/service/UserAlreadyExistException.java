package com.sem.keeper.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserAlreadyExistException extends Throwable {
    
    private static final long serialVersionUID = -4830789234331253792L;

    public UserAlreadyExistException(@NotNull @NotEmpty String s) {
    }
}
