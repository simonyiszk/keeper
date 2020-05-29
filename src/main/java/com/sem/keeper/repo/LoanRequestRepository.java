package com.sem.keeper.repo;

import com.sem.keeper.entity.LoanRequestEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRequestRepository extends PagingAndSortingRepository<LoanRequestEntity, Long> {

    LoanRequestEntity findById(long id);

    List<LoanRequestEntity> findAll();
}
