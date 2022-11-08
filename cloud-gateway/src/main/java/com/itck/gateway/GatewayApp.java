package com.itck.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }

    @Bean
    @Order(0)
    public GlobalFilter first() {
        return (exchange, chain) -> {
            System.out.println("first");
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> System.out.println("second-post-0 过滤器执行了"))
            );
        };
    }

    @Bean
    @Order(-1)
    public GlobalFilter two() {
        return (exchange, chain) -> {
            System.out.println("two");
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> System.out.println("second-post--1 过滤器执行了"))
            );
        };
    }

    @Bean
    @Order(1)
    public GlobalFilter three() {
        return (exchange, chain) -> {
            System.out.println("three");
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> System.out.println("second-post-1 过滤器执行了"))
            );
        };
    }
}