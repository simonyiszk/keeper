package com.sem.keeper.service;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanRequestEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class LoanRequestService {

    @Autowired
    LoanRequestRepository loanRequestRepository;

    @Autowired
    LoanService loanService;

    public LoanRequestEntity addLoanRequest(UserEntity user, DeviceEntity deviceEntity){
        LoanRequestEntity neu = new LoanRequestEntity(user, deviceEntity);
        neu.setCreationDate(LocalDateTime.now());
        return loanRequestRepository.save(neu);
    }

    public void accept(LoanRequestEntity loanRequestEntity, UserEntity kiad) throws DeviceAlreadyOnLoanException {
        loanService.fromLoanRequest(loanRequestEntity, kiad);
        loanRequestRepository.delete(loanRequestEntity);
    }

    public void deny(long id){
        loanRequestRepository.deleteById(id);
    }
}
