package org.itck.orders.mapper;

import com.itck.entity.dto.OrderDetailDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.itck.orders.entity.TOrderitem;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    // 根据订单ID查询订单详情,详情中还包含商品信息，所以要联表查询订单详细表和商品表
    @Select("select oi.*,g.title,g.status,g.small_pic from t_orderitem oi left join t_goods g on oi.gid = g.id where oi.oid=#{oid}")
    List<OrderDetailDto> selectByOid(int oid);

    // 新增订单详情的方法
    @Insert("INSERT INTO t_orderitem(oid,gid,price,num) VALUES(#{oid},#{gid},#{price},#{num})")
    int insert(TOrderitem item);

}
