package org.itck.goods.mapper;

import com.itck.entity.domain.R;
import com.itck.entity.domain.TbGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TbGoodsMapper {

    @Select("select * from tb_goods where goods_id=#{id}")
    TbGoods findById(Integer id);

    @Update("update tb_goods set goods_stock=#{goodsStock} where goods_id=#{goodsId}")
    int update(@Param("goodsId") Integer goodsId, @Param("goodsStock") Integer goodsStock);

}
