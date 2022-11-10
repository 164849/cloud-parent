package com.itck.coupon.mapper;

import com.itck.coupon.entity.TUsercoupon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TUsercouponDao {

    void insertBatch(List<TUsercoupon> record);

    int deleteByPrimaryKey(Integer id);

    int insert(TUsercoupon record);

    int insertSelective(TUsercoupon record);

    TUsercoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUsercoupon record);

    int updateByPrimaryKey(TUsercoupon record);
}