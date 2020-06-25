package com.sem.keeper.service;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.LoanRequestEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.DeviceRepository;
import com.sem.keeper.repo.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    DeviceRepository deviceRepository;

    public LoanEntity fromLoanRequest(LoanRequestEntity loanRequestEntity, UserEntity kiad) throws DeviceAlreadyOnLoanException {
        if (loanRequestEntity.getDeviceEntity().isKiadva()){
            throw new DeviceAlreadyOnLoanException("Device is already on loan");
        }

        LoanEntity neu = new LoanEntity(loanRequestEntity, kiad);
        return loanRepository.save(neu);
    }

    public LoanEntity newLoan(DeviceEntity deviceEntity, UserEntity kiad, UserEntity kivesz) throws DeviceAlreadyOnLoanException {
        if (deviceEntity.isKiadva()) throw new DeviceAlreadyOnLoanException("F");
        LoanEntity toAdd = new LoanEntity();
        toAdd.setDeviceEntity(deviceEntity);
        toAdd.setKiadta(kiad);
        toAdd.setElvitte(kivesz);
        toAdd.setTakeDate(LocalDateTime.now());
        toAdd.setBackDatePlanned(LocalDateTime.now().plusDays(7));
        return loanRepository.save(toAdd);
    }

    public void hosszabbit(LoanEntity loanEntity){
        loanEntity.setBackDatePlanned(LocalDateTime.now().plusDays(7));
    }

    public void visszahoz(LoanEntity loanEntity, UserEntity backUser) {
        loanEntity.setBackDateReal(LocalDateTime.now());
        loanEntity.setVisszavette(backUser);
    }
}
