package com.itck.goodsddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.itck.entity.ddd")
public class GoodsDddApp {
    public static void main(String[] args) {
        SpringApplication.run(GoodsDddApp.class, args);
    }
}
