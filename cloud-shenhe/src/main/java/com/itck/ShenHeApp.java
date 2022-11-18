package com.itck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShenHeApp {

    public static void main(String[] args) {
        SpringApplication.run(ShenHeApp.class, args);
    }
}
