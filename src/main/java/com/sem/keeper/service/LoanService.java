package com.sem.keeper.service;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    public LoanEntity newLoan(DeviceEntity deviceEntity, UserEntity chad, UserEntity virgin){
        LoanEntity toAdd = new LoanEntity();
        toAdd.setDeviceEntity(deviceEntity);
        toAdd.setKiadta(chad);
        toAdd.setElvitte(virgin);
        toAdd.setTakeDate(LocalDateTime.now());
        toAdd.setBackDatePlanned(LocalDateTime.now().plusDays(7));
        return loanRepository.save(toAdd);
    }

}
