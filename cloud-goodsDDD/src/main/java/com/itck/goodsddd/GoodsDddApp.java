package com.itck.goodsddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GoodsDddApp {
    public static void main(String[] args) {
        SpringApplication.run(GoodsDddApp.class, args);
    }
}
