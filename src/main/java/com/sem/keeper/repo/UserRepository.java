package com.sem.keeper.repo;

import com.sem.keeper.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    Page<UserEntity> findByFirstNameContainingOrLastNameContainingIgnoreCase(String firstName, String lastName, Pageable pageable);

}
