package com.itck.coupon.controller;

import com.itck.coupon.dto.CouponAuditDto;
import com.itck.coupon.entity.TCouponTemplate;
import com.itck.coupon.service.CouponService;
import com.itck.entity.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("coupon")
public class CouponTemController {

    private final CouponService couponService;

    // 新增优惠券模板
    @PostMapping("save")
    public R save(@RequestBody TCouponTemplate couponTemplate) {
        return couponService.save(couponTemplate);
    }

    // 分页查看优惠券列表
    @GetMapping("queryAll")
    public R queryAll(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return couponService.queryAll(pageIndex, pageSize);
    }

    // 优惠券审核接口
    @PostMapping("audit")
    public R audit(@RequestBody CouponAuditDto dto) {
        return couponService.audit(dto);
    }

    
}
