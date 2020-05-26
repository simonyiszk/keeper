package com.sem.keeper.repo;

import com.sem.keeper.entity.DeviceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends PagingAndSortingRepository<DeviceEntity, Long> {
    List<DeviceEntity> findByName(String name);

    List<DeviceEntity> findByDescription(String description);

    DeviceEntity findById(long id);

    List<DeviceEntity> findByNameContainingIgnoreCase(String name);
}
