package com.sem.keeper.service

import com.sem.keeper.entity.GoodMusicEntity
import com.sem.keeper.repo.GoodMusicRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class GoodMusicService(
    private val goodMusicRepository: GoodMusicRepository
) {

    private var random = Random()
    val nextGoodMusic: GoodMusicEntity?
        get() {
            val musicCount = goodMusicRepository.count().toInt()
            val id = random.nextInt(musicCount)
            val goodMusicEntityPage = goodMusicRepository.findAll(PageRequest.of(id, 1))
            return if (goodMusicEntityPage.hasContent()) {
                goodMusicEntityPage.content[0]
            } else {
                null
            }
        }
}