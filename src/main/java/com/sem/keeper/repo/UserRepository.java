package com.sem.keeper.repo;

import com.sem.keeper.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    Page<UserEntity> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);

    Optional<UserEntity> findByAuthSchId(String authSchId);

}
