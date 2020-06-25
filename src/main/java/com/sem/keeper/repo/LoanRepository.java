package com.sem.keeper.repo;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LoanRepository extends PagingAndSortingRepository<LoanEntity, Long> {

    List<LoanEntity> findByElvitte(UserEntity elvitte);

    Page<LoanEntity> findByElvitte(UserEntity elvitte, Pageable pageRequest);

    //List<LoanEntity> findByDeviceEntityAndVisszavetteIsNullOrderByTakeDate(DeviceEntity deviceEntity);

    List<LoanEntity> findByVisszavetteIsNullOrderByTakeDate();

    Page<LoanEntity> findByVisszavetteIsNullOrderByTakeDate(Pageable pageable);
}
