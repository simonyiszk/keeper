package com.sem.keeper.service;

import com.sem.keeper.model.UserRegDto;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.UserRepository;
import hu.gerviba.authsch.struct.CardType;
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
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder pe;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public void makeMember(UserEntity userEntity){
        userEntity.getRoles().add("ROLE_MEMBER");
        userEntity.setKiadhat(true);
        //repository.save(userEntity);
    }

    public void unMakeMember(UserEntity userEntity){
        userEntity.getRoles().remove("ROLE_MEMBER");
        userEntity.setKiadhat(false);
        //repository.save(userEntity);
    }

    public void updateCard(UserEntity user, CardType card){
        user.setCardType(card);
    }

    public void deleteUser(UserEntity userEntity){
        repository.delete(userEntity);
    }

    public UserEntity registerNewUserAccount(UserRegDto userRegDto) throws UserAlreadyExistException {

        if (emailExist(userRegDto.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: " + userRegDto.getEmail());
        }

        UserEntity user = new UserEntity();

        user.setFullName(userRegDto.getFirstName()+" "+userRegDto.getLastName());

        user.setPassword(pe.encode(userRegDto.getPassword()));
        user.setEmail(userRegDto.getEmail());
        user.setRoles(Arrays.asList("ROLE_USER"));

        user.setValid(false);

        return repository.save(user);
    }

    private boolean emailExist(String email) {
        return repository.findByEmail(email) != null;
    }
}
