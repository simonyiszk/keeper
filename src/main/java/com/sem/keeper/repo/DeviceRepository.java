package com.sem.keeper.repo;

import com.sem.keeper.entity.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Eszköz entitások elérésére használó repository
 */
@Repository
public interface DeviceRepository extends PagingAndSortingRepository<DeviceEntity, Long> {

    DeviceEntity findById(long id);

    Page<DeviceEntity> findByNameContainingIgnoreCase(String name, Pageable page);
}
