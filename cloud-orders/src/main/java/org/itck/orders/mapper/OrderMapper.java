package org.itck.orders.mapper;

import org.apache.ibatis.annotations.*;
import org.itck.orders.entity.TOrder;

@Mapper
public interface OrderMapper {

    // 根据订单ID修改订单状态
    @Update("update t_order set flag=#{flag} where id=#{id}")
    int updateFlag(@Param("id") int id, @Param("flag") int flag);

    // 新增订单的方法
    int save(TOrder order);

    @Select("SELECT * FROM t_order where `no`=#{no}")
    TOrder findByNo(String no);


}
