package org.itck.orders.controller;

import com.itck.entity.domain.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    RestTemplate template;

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

}
