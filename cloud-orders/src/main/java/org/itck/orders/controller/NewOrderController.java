package org.itck.orders.controller;

import com.itck.entity.domain.R;
import com.itck.entity.dto.OrderAddDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itck.orders.service.NewOrderService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("newOrder")
public class NewOrderController {
    private final NewOrderService newOrderService;

    /**
     * 下单接口第一版
     *
     * @param dto dto
     * @param uid uid
     * @return {@link R}
     */
    @PostMapping("save")
    public R save(@RequestBody OrderAddDto dto, @RequestHeader("uid") int uid) {
        // 参数校验
        Assert.notNull(uid, "用户ID不能为空");
        Assert.notNull(dto.getGid(), "商品ID不能为空");
        Assert.notNull(dto.getUaid(), "收货地址不能为空");
        Assert.isTrue(dto.getNum() > 0, "下单商品数量必须大于0");
        return newOrderService.save(dto, uid);
    }
}
