package com.sem.keeper.service;

import com.sem.keeper.model.UserRegDto;
import com.sem.keeper.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    UserEntity registerNewUserAccount(UserRegDto userRegDto)
            throws UserAlreadyExistException;
}
