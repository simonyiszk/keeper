package com.sem.keeper.repo;

import com.sem.keeper.entity.LoanRequestEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestRepository extends PagingAndSortingRepository<LoanRequestEntity, Long> {
}
