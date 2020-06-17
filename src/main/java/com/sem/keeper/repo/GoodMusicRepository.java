package com.sem.keeper.repo;

import com.sem.keeper.entity.GoodMusicEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodMusicRepository extends PagingAndSortingRepository<GoodMusicEntity, Long> {

    @Override
    @NotNull
    List<GoodMusicEntity> findAll();

    @NotNull
    Page<GoodMusicEntity> findAll(@NotNull Pageable pageable);

}
