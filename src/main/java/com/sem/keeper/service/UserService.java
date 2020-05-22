package com.sem.keeper.service;

import com.sem.keeper.model.UserRegDto;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Arrays;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder pe;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserEntity getFromPrincipal(@NotNull Principal p){
        return repository.findByEmail(p.getName());
    }

    public void makeMember(@NotNull UserEntity userEntity){
        userEntity.getRoles().add("ROLE_MEMBER");
        repository.save(userEntity);
    }

    public UserEntity registerNewUserAccount(@NotNull UserRegDto userRegDto) throws UserAlreadyExistException {

        if (emailExist(userRegDto.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: " + userRegDto.getEmail());
        }

        UserEntity user = new UserEntity();

        user.setFirstName(userRegDto.getFirstName());
        user.setLastName(userRegDto.getLastName());


        user.setPassword(pe.encode(userRegDto.getPassword()));
        user.setEmail(userRegDto.getEmail());
        user.setRoles(Arrays.asList("ROLE_USER"));

        user.setValid(false);

        return repository.save(user);
    }

    private boolean emailExist(String email) {
        return repository.findByEmail(email) != null;
    }

    public UserEntity findByEmail(String email){
        return repository.findByEmail(email);
    }
}
