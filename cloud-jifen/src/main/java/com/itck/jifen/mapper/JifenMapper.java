package com.itck.jifen.mapper;


import com.itck.entity.domain.Integral;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JifenMapper {

    @Insert("insert into integral (order_id, integral) values (#{orderId},#{integral})")
    int saveJiFen(Integral integral);
}
