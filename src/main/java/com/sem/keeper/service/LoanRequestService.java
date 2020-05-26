package com.sem.keeper.service;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanRequestEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.LoanRepository;
import com.sem.keeper.repo.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoanRequestService {

    @Autowired
    LoanRequestRepository loanRequestRepository;

    public LoanRequestEntity addLoanRequest(UserEntity user, DeviceEntity deviceEntity){
        LoanRequestEntity neu = new LoanRequestEntity(user, deviceEntity);
        return loanRequestRepository.save(neu);
    }
}
