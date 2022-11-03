package org.itck.orders.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("test")
    public String test(@RequestHeader("name") String name, @RequestParam("age") String age) {
        System.out.println("获取到的请求头中的name=" + name);
        System.out.println("获取到的请求参数的age=" + age);
        return "Order Test! name=" + name + ",age=" + age;
    }
}
