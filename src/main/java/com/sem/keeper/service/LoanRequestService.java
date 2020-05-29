package com.sem.keeper.service;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanEntity;
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

    @Autowired
    LoanService loanService;

    public LoanRequestEntity addLoanRequest(UserEntity user, DeviceEntity deviceEntity){
        LoanRequestEntity neu = new LoanRequestEntity(user, deviceEntity);
        return loanRequestRepository.save(neu);
    }

    public void accept(LoanRequestEntity loanRequestEntity, UserEntity kiad){
        LoanEntity neu = new LoanEntity(loanRequestEntity, kiad);
        loanService.newLoan(neu);
        loanRequestRepository.delete(loanRequestEntity);
    }

    public void deny(long id){
        loanRequestRepository.deleteById(id);
    }
}
