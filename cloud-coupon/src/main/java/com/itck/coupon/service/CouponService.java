package com.itck.coupon.service;

import com.github.pagehelper.PageInfo;
import com.itck.coupon.dto.CouponAuditDto;
import com.itck.coupon.entity.TCouponTemplate;
import com.itck.entity.domain.R;

public interface CouponService {

    // 新增优惠券
    R save(TCouponTemplate template);

    // 分页查询优惠券列表
    R<PageInfo<TCouponTemplate>> queryAll(Integer pageIndex, Integer pageSize);

    // 审核优惠券活动
    R audit(CouponAuditDto dto);


}
