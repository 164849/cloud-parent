package com.itck.coupon.listener;

import com.itck.common.utils.DateUtil;
import com.itck.common.utils.RedissonUtils;
import com.itck.coupon.bo.MqMsgBo;
import com.itck.coupon.config.RabbitMQConstConfig;
import com.itck.coupon.config.RedisKeyConfig;
import com.itck.coupon.entity.TCouponTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@RabbitListener(queues = RabbitMQConstConfig.Q_COUPONUSE)
public class CouponTemUserListener {


    @RabbitHandler
    public void handler(MqMsgBo bo) {
        //判断优惠券类型
        if (bo.getType() == RabbitMQConstConfig.MQTYPE_COUPONUSE) {
            TCouponTemplate template = (TCouponTemplate) bo.getData();
            Integer templateId = template.getId();
            //指定优惠券模板的缓存key
            String key = RedisKeyConfig.COUPON_CACHE + templateId;
            RedissonUtils.setList(key, template.getCouponCount());
            RedissonUtils.setList(key, template.getTargetLevel());

            RedissonUtils.expire(key, DateUtil.lastSeconds(template.getExpireTime()));
            log.info("用户优惠券发放成功!");
        }

    }
}
