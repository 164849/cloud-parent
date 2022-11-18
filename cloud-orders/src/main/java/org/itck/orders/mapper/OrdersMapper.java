package org.itck.orders.mapper;

import com.itck.entity.domain.TbOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper {

    @Insert("insert into tb_order values(#{orderId},#{orderNum},#{orderAmount},#{goodsId})")
    int save(TbOrder tbOrder);
}
