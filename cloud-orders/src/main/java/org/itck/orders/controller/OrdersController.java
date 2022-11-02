package org.itck.orders.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("order")
public class OrdersController {

    @GetMapping("test001")
    @SentinelResource("test001")
    public String test9(String name) {
        return "Test001! " + name;
    }

    @GetMapping("test002")
    @SentinelResource("test002")
    public String test11(String flag) {
        if (Objects.isNull(flag)) {
            throw new IllegalArgumentException("非法参数异常！");
        }
        return "Test002! " + flag;
    }
}
