package com.itck.gateway.filter;

import com.itck.gateway.config.MyConfig;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class CalServiceTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<MyConfig> {

    public CalServiceTimeGatewayFilterFactory() {
        super(MyConfig.class);
    }

    @Override
    public GatewayFilter apply(MyConfig config) {

        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                //前处理
                long startTime = System.currentTimeMillis();

                System.out.println("name:" + config.getKey());
                System.out.println("value:" + config.getValue());

//                return chain.filter(exchange);//放行

                return chain.filter(exchange).then(
                        //后置处理
                        Mono.fromRunnable(() -> {
                            System.out.println("post come in");
                            //获取系统当前时间戳为endTime
                            long entTime = System.currentTimeMillis();
                            System.out.println("time=" + (entTime - startTime));

                        }));
            }
        };
    }

    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.DEFAULT;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("key", "value");
    }
}