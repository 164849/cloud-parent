package com.itck.jifen.controller;

import com.itck.entity.domain.Jifen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jifen")
@RefreshScope
public class JifenController {

    @Value("${url}")
    String url;

    @GetMapping("test01")
    public String test01() {
        return "配置信息是:" + url;
    }

    @PostMapping(value = "/save")
    public Map save(@RequestBody Jifen jifen) {

        System.out.println("调用了积分保存接口");
        System.out.println(jifen);
        return new HashMap() {{
            put("isSuccess", true);
            put("msg", "save success");
        }};
    }

    @PostMapping(value = "/update")
    public Map update(@RequestBody Jifen jifen) {

        System.out.println(jifen);
        return new HashMap() {{
            put("isSuccess", true);
            put("msg", "update success");
        }};

    }

    @GetMapping(value = "/delete")
    public Map deleteById(Integer jifenId) {
        System.out.println("删除id为" + jifenId + "的积分信息");
        return new HashMap() {{
            put("isSuccess", true);
            put("msg", "delete success");
        }};

    }


    @GetMapping(value = "/{jifenId}")
    public Jifen findJifenById(@PathVariable Integer jifenId) {
        System.out.println("已经查询到" + jifenId + "积分数据");
        return new Jifen(jifenId, 12, "消费积分");
    }


    @GetMapping(value = "/search")
    public Jifen search(Integer uid, String type) {
        System.out.println("uid:" + uid + "type:" + type);
        return new Jifen(uid, 12, type);
    }

    @PostMapping(value = "/searchByEntity")
    public List<Jifen> searchMap(@RequestBody Jifen jifen) {

        System.out.println(jifen);

        List<Jifen> jifens = new ArrayList<Jifen>();
        jifens.add(new Jifen(110, 12, "下单积分"));
        jifens.add(new Jifen(111, 18, "支付积分"));
        return jifens;
    }

}