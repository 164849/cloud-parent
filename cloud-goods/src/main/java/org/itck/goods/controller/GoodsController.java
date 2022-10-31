package org.itck.goods.controller;

import com.itck.entity.domain.Goods;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @RequestMapping("findById/{id}")
    public Goods findById(@PathVariable Integer id) {

        System.out.println("id" + id);
        return new Goods(id, "小米", 99);
    }

}