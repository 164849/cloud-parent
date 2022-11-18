package com.itck.skill.config;

import com.itck.common.config.RabbitMQConstConfig;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Description:
 * @Author: zed
 * @Date: 2022/5/19 10:08
 */
@Configuration
public class RabbitMQConfig {
    //创建队列
    @Bean
    public Queue createq1() {
        return new Queue(RabbitMQConstConfig.Q_SKILLORDER_SYNC);
    }

    @Bean
    public Queue createq2() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", RabbitMQConstConfig.EX_DEAD);
        params.put("x-dead-letter-routing-key", RabbitMQConstConfig.RK_DEAD_SKILLORDERTO);
        params.put("x-message-ttl", SystemConfig.SKILL_ORDER_TIMEOUT);
        return QueueBuilder.durable(RabbitMQConstConfig.Q_SKILLORDER_DEAD).withArguments(params).build();
    }

    @Bean
    public Queue createq3() {
        return new Queue(RabbitMQConstConfig.Q_SKILLORDER_TIMEOUT);
    }

    //创建交换器
    @Bean
    public FanoutExchange createFe() {
        return new FanoutExchange(RabbitMQConstConfig.EX_SKILLORDERADD);
    }

    @Bean
    public DirectExchange createDe() {
        return new DirectExchange(RabbitMQConstConfig.EX_DEAD);
    }

    //创建绑定
    @Bean
    public Binding createB1(FanoutExchange fe) {
        return BindingBuilder.bind(createq1()).to(fe);
    }

    @Bean
    public Binding createB2(FanoutExchange fe) {
        return BindingBuilder.bind(createq2()).to(fe);
    }

    @Bean
    public Binding createB3(DirectExchange de) {
        return BindingBuilder.bind(createq3()).to(de).with(RabbitMQConstConfig.RK_DEAD_SKILLORDERTO);
    }

}
