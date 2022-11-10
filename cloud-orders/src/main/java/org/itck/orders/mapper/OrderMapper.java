package org.itck.orders.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.itck.orders.entity.TOrder;

@Mapper
public interface OrderMapper {

    // 根据订单ID修改订单状态
    @Update("update t_order set flag=#{flag} where id=#{id}")
    int updateFlag(@Param("id") int id, @Param("flag") int flag);

    // 新增订单的方法
    @Insert("insert into t_order(uid, uaid, total_money, pay_money, free_money, pay_type, flag, create_time, update_time, `no`) " +
            "values (#{uid},#{uaid},#{totalMoney},#{payMoney},#{freeMoney},#{payType},#{flag},#{createTime},#{updateTime},#{no})")
    int save(TOrder order);

}
