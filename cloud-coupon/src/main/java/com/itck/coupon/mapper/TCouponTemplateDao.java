package com.itck.coupon.mapper;


import com.itck.coupon.entity.TCouponTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TCouponTemplateDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TCouponTemplate record);

    int insertSelective(TCouponTemplate record);

    TCouponTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCouponTemplate record);

    int updateByPrimaryKey(TCouponTemplate record);

    List<TCouponTemplate> findAll();
}