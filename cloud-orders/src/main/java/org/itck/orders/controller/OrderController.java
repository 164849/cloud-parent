package org.itck.orders.controller;

import com.itck.entity.domain.Goods;
import com.itck.entity.domain.Jifen;
import org.itck.orders.api.JifenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    RestTemplate template;

    @Resource
    JifenApi jifenApi;

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
}
