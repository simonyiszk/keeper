package com.sem.keeper.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SemLogSpammer {

    private static final Logger log = LoggerFactory.getLogger(SemLogSpammer.class);

    @EventListener
    public void onStartup(ApplicationReadyEvent event) {
        log.info("Test users:");
        log.info("gazsi69@gmail.com:aluminium");
        log.info("airplaneboy@gmail.com:mekk");
        log.info("a@a:a");
        //log.info("Test users:");
        //log.info("Test users:");
    }
}
