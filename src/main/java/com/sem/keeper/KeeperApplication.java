package com.sem.keeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:git.properties")
public class KeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeeperApplication.class, args);
    }

}
