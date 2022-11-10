package com.itck.coupon.controller;

import com.itck.coupon.config.ArgumentErrorConstant;
import com.itck.coupon.service.CouponService;
import com.itck.entity.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("userCoupon")
public class UserCouponController {

    private final CouponService couponService;

    @PostMapping("receive")
    public R save(Integer uid, Integer rl, Integer ctid) {
        Assert.notNull(uid, ArgumentErrorConstant.ID_NOT_NULL);
        Assert.notNull(rl, ArgumentErrorConstant.UL_NOT_NULL);
        Assert.notNull(ctid, ArgumentErrorConstant.CTID_NOT_NULL);
        return couponService.receive(uid, rl, ctid);
    }
}
