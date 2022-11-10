package org.itck.orders.service.impl;

import com.itck.common.utils.RedissonUtils;
import com.itck.entity.domain.Integral;
import com.itck.entity.domain.R;
import com.itck.entity.domain.TbGoods;
import com.itck.entity.domain.TbOrder;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itck.orders.api.GoodsApi;
import org.itck.orders.api.JifenApi;
import org.itck.orders.mapper.OrdersMapper;
import org.itck.orders.service.OrderService;

import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrdersMapper mapper;

    private final GoodsApi goodsApi;

    private final JifenApi jifenApi;

    @Override
    //本地事务注解
    //@Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    public R saveOrder(TbOrder order) {
        log.info("开始下单,商品ID是{}", order.getGoodsId());
        String uuid = UUID.randomUUID().toString();
        RLock rLock = RedissonUtils.getLock("uuid");


        try {
            if (rLock.tryLock(30, TimeUnit.SECONDS)) {
                R<TbGoods> goodsR = goodsApi.quireById(order.getGoodsId());
                if (Objects.equals(200, goodsR.getCode())) {
                    TbGoods goods = goodsR.getData();
                    if (goods.getGoodsStock() >= order.getOrderNum()) {
                        goods.setGoodsStock(goods.getGoodsStock() - order.getOrderNum());
                        goodsApi.updateById(goods);

                        order.setOrderId(uuid);

                        Integral integral = new Integral();
                        integral.setOrderId(uuid);
                        jifenApi.save(integral);

                        mapper.save(order);
                        //int i = 1 / 0;
                        return R.ok();
                    } else {
                        return R.fail("商品数量不足");
                    }
                } else {
                    return R.fail("未知错误！！！");
                }
            } else {
                return R.fail("亲，没有了！");
            }
        } catch (InterruptedException e) {
            return R.fail("系统故障");
        } finally {
            rLock.unlock();
        }


    }
}
