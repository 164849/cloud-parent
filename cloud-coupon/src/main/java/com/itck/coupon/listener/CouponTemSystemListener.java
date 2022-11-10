package com.itck.coupon.listener;

import com.itck.coupon.bo.MqMsgBo;
import com.itck.coupon.config.RabbitMQConstConfig;
import com.itck.coupon.config.SystemConfig;
import com.itck.coupon.config.ThreadPoolSignle;
import com.itck.coupon.entity.TCouponTemplate;
import com.itck.coupon.entity.TUsercoupon;
import com.itck.coupon.mapper.TCouponTemplateDao;
import com.itck.coupon.mapper.TUsercouponDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
@RabbitListener(queues = RabbitMQConstConfig.Q_COUPONSYS)
public class CouponTemSystemListener {

    private final TUsercouponDao dao;

    @RabbitHandler
    public void handler(MqMsgBo bo) {
        if (bo.getType() == RabbitMQConstConfig.MQTYPE_COUPONSYS) {
            TCouponTemplate template = (TCouponTemplate) bo.getData();

            Integer target = template.getTarget();

            List<Integer> uIds = new ArrayList<>();
            if (target == SystemConfig.COUPON_TARGET_ALL) {
                //说明优惠券是发放给全体人员,查询全部用户的ID
                uIds = getLevelUser(target);
            } else if (target == SystemConfig.COUPON_TARGET_LEVEL) {
                //如果是发放给某个等级的用户嘛，查一下发给哪个等级
                Integer targetLevel = template.getTargetLevel();

                //根据等级查到这个等级的所有用户
                uIds = getLevelUser(targetLevel);
            }

            // 使用templateDao到用户优惠券表中批量新增数据
            Integer templateId = template.getId();
            String templateKey = template.getTemplateKey();
//            uIds.forEach(id -> {
//                // 新增用户优惠券对象,第一版由于数据量太多，这种方式不可取，第二版需要优化
//                dao.insert(new TUsercoupon(templateId, id, templateKey));
//            });
            //分片处理
            int batches = uIds.size() / SystemConfig.THREAD_COUPON_BATCH;
            batches = uIds.size() % SystemConfig.THREAD_COUPON_BATCH == 0 ? batches : batches + 1;

            for (int i = 0; i < batches; i++) {
                int start = i * SystemConfig.THREAD_COUPON_BATCH;
                List<Integer> finalUids = uIds;

                ThreadPoolSignle.getInstance().poolExecutor.execute(() -> {
                    // 获取当前批次的id集合
                    List<Integer> list = finalUids.stream().skip(start).limit(SystemConfig.THREAD_COUPON_BATCH).collect(Collectors.toList());
                    List<TUsercoupon> record = new ArrayList<>();
                    list.forEach(id -> record.add(new TUsercoupon(templateId, id, templateKey)));
                    dao.insertBatch(record);
                });
            }

        }
    }


    private List<Integer> getLevelUser(Integer targetLevel) {
        return Stream.iterate(1, id -> id + 1).limit(5100).collect(Collectors.toList());
    }
}
