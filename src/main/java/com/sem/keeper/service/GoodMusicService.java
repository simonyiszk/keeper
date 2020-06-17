package com.sem.keeper.service;

import com.sem.keeper.entity.GoodMusicEntity;
import com.sem.keeper.repo.GoodMusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GoodMusicService {

    @Autowired
    GoodMusicRepository goodMusicRepository;

    Random random = new Random();

    public GoodMusicEntity getNextGoodMusic(){
        int musicCount =  (int) goodMusicRepository.count();
        int id = random.nextInt(musicCount);
        Page<GoodMusicEntity> goodMusicEntityPage = goodMusicRepository.findAll(PageRequest.of(id,1));
        if(goodMusicEntityPage.hasContent()){
            return goodMusicEntityPage.getContent().get(0);
        } else {
            return null;
        }
    }

}
