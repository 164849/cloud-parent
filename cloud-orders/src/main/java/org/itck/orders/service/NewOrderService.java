package org.itck.orders.service;

import com.itck.entity.domain.R;
import com.itck.entity.dto.OrderAddDto;
import com.itck.entity.dto.OrderFlagDto;

public interface NewOrderService {

    //商品详情-下单
    R save(OrderAddDto dto, int uid);

    //商品详情-下单-削峰填谷(MQ Redis)
    R save2(OrderAddDto dto, int uid);

    //查询我的订单
    R queryMy(int uid, int flag);

    //更改订单状态
    R updateFlag(OrderFlagDto dto);

    //取消订单
    R cancel(String no, int uid);
}
