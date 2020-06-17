package com.sem.keeper.repo;

import com.sem.keeper.entity.LoanRequestEntity;
import com.sem.keeper.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LoanRequestRepository extends PagingAndSortingRepository<LoanRequestEntity, Long> {

    LoanRequestEntity findById(long id);

    Set<LoanRequestEntity> findByElvinne(UserEntity elvinne);

    List<LoanRequestEntity> findAll();
}
