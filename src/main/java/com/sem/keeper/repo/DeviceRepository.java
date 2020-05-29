package com.sem.keeper.repo;

import com.sem.keeper.entity.DeviceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Eszköz entitások elérésére használó repository
 */
@Repository
public interface DeviceRepository extends PagingAndSortingRepository<DeviceEntity, Long> {

    DeviceEntity findById(long id);

    List<DeviceEntity> findByNameContainingIgnoreCase(String name);
}
