package com.atome.atomelearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AtomeLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtomeLearnApplication.class, args);
    }

}
