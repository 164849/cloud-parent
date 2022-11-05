package com.itck.jifen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JiFenApp {
    
    public static void main(String[] args) {
        SpringApplication.run(JiFenApp.class, args);
    }
}
