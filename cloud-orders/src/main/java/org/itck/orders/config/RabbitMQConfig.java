package org.itck.orders.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitMQConfig {
    //创建队列，数据同步，这个队列中的消息消费后同步到数据库
    @Bean
    public Queue createq1() {
        return new Queue(RabbitMQConstConfig.Q_ORDER_SYNC);
    }

    // 死信队列 这个队列不需要有消费者
    // 消息进入到这个队列后 到了队列设置的过期时间会进入到死信交换机
    // 然后通过指定的routingkey 进入绑定的延迟队列createq3()=>Q_ORDER_TIMEOUT
    @Bean
    public Queue createq2() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", RabbitMQConstConfig.EX_DEAD);
        params.put("x-dead-letter-routing-key", RabbitMQConstConfig.RK_DEAD_ORDERTO);
        params.put("x-message-ttl", SystemConfig.ORDER_TIMEOUT);
        return QueueBuilder.durable(RabbitMQConstConfig.Q_ORDER_DEAD).withArguments(params).build();
    }

    // 延迟队列
    @Bean
    public Queue createq3() {
        return new Queue(RabbitMQConstConfig.Q_ORDER_TIMEOUT);
    }

    // 创建交换器-下单的交换机 广播类型的
    // 进入这个交换机的消息 通过广播给同步队列和延迟队列
    @Bean
    public FanoutExchange createFe() {
        return new FanoutExchange(RabbitMQConstConfig.EX_ORDERADD);
    }

    // 死信交换机
    @Bean
    public DirectExchange createDe() {
        return new DirectExchange(RabbitMQConstConfig.EX_DEAD);
    }

    // 创建绑定 同步队列-他的消费者进行下单同步到数据库
    @Bean
    public Binding createB1(FanoutExchange fe) {
        return BindingBuilder.bind(createq1()).to(fe);
    }

    // 创建绑定 死信队列-没有消费者
    @Bean
    public Binding createB2(FanoutExchange fe) {
        return BindingBuilder.bind(createq2()).to(fe);
    }

    // 创建绑定 延迟队列 - 消息过期 设置订单超时和回退库存
    @Bean
    public Binding createB3(DirectExchange de) {
        return BindingBuilder.bind(createq3()).to(de).with(RabbitMQConstConfig.RK_DEAD_ORDERTO);
    }

}
