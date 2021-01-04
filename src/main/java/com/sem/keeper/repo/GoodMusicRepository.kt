package com.sem.keeper.repo

import com.sem.keeper.entity.GoodMusicEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface GoodMusicRepository : PagingAndSortingRepository<GoodMusicEntity, Long> {
    override fun findAll(): List<GoodMusicEntity>
}