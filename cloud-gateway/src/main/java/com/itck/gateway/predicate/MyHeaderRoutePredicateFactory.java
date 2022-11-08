package com.itck.gateway.predicate;

import com.itck.gateway.config.MyConfig;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class MyHeaderRoutePredicateFactory extends AbstractRoutePredicateFactory<MyConfig> {
    public MyHeaderRoutePredicateFactory() {
        super(MyConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyConfig config) {
        return exchange -> {

            if (StringUtils.isEmpty(config.getValue())) {//只配置了key，但是没有配置value

                if (exchange.getRequest().getHeaders().containsKey(config.getKey())) {
                    return true;
                } else {
                    return false;
                }

            } else {//同时配置了key和value

                //根据key获取value
                String value = exchange.getRequest().getHeaders().getFirst(config.getKey());
                if (config.getValue().equals(value)) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("key", "value");
    }
}