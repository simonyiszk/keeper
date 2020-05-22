package com.sem.keeper.repo;

import com.sem.keeper.entity.LoanEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends PagingAndSortingRepository<LoanEntity, Long> {

}
