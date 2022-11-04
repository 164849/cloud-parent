package org.itck.orders.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.itck.entity.domain.Goods;
import com.itck.entity.domain.Jifen;
import com.itck.entity.domain.R;
import com.itck.entity.domain.TbOrder;
import lombok.RequiredArgsConstructor;
import org.itck.orders.api.JifenApi;
import org.itck.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    RestTemplate template;

    @Resource
    JifenApi jifenApi;

    private final OrderService service;

    @PostMapping("save")
    public R saveOrder(@RequestBody TbOrder tbOrder) {
        if (Objects.isNull(tbOrder.getGoodsId())) {
            throw new IllegalArgumentException("商品id不能为空");
        }
        if (tbOrder.getOrderNum() < 0) {
            throw new IllegalArgumentException("购买数量不能小于0");
        }
        return service.saveOrder(tbOrder);
    }

    @GetMapping("save")

    public Object save() {
        //1、调用商品服务，查询商品信息

        String url = "http://cloud-goods/goods/findById/1";
        Goods goods = template.getForObject(url, Goods.class);
        System.out.println(goods);
        System.out.println("保存订单成功");
        return new HashMap<String, Object>() {{
            put("code", 200);
            put("msg", "success");
        }};

    }

    //
    @GetMapping("test")
    public Map saveJifen() {
        Jifen jifen = new Jifen(1, 10, "注册送大奖");
        return jifenApi.save(jifen);
    }

    @GetMapping("test02")
    public String test02() {
        return "流量测试接口1";
    }

    @GetMapping("test03")
    public String test03() {
        Jifen jifen = new Jifen(1, 10, "注册送大奖");
        jifenApi.save(jifen);
        return "链路接口限流-3-请求限流";
    }

    @GetMapping("test04")
    public String test04() {
        Jifen jifen = new Jifen(1, 10, "注册送大奖");
        jifenApi.save(jifen);
        return "链路接口限流-4-请求不限流";
    }

    @GetMapping("test05")
    public String test05(String flag) throws InterruptedException {

        if (Objects.isNull(flag)) {
            TimeUnit.MILLISECONDS.sleep(1800);
        }
        return "慢调用比例的熔断";
    }

    @GetMapping("test06")
    public String test06(String flag) throws Exception {
        if (flag == null) {
            throw new IllegalArgumentException("参数异常");
        }
        return "test6";
    }

    @GetMapping("test07")
    @SentinelResource("test07-hotkey")
    public String test07(String name) {
        System.out.println(name);
        return name;
    }
}
