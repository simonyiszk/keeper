package com.sem.keeper.repo

import com.sem.keeper.entity.DeviceEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : PagingAndSortingRepository<DeviceEntity, Long> {
    fun findByNameContainingIgnoreCase(name: String?, page: Pageable?): Page<DeviceEntity?>
}