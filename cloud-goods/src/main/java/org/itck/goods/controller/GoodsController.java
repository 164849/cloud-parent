package org.itck.goods.controller;

import com.itck.entity.domain.Goods;
import com.itck.entity.domain.R;
import com.itck.entity.domain.TbGoods;
import lombok.RequiredArgsConstructor;
import org.itck.goods.service.TbGoodsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("goods")
@RequiredArgsConstructor
public class GoodsController {

    private final TbGoodsService service;

    @GetMapping("quireById")
    public R<TbGoods> quireById(@RequestParam("id") Integer id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        return service.findById(id);
    }

    @PostMapping("updateById")
    public R<TbGoods> updateById(@RequestBody TbGoods tbGoods) {
        if (Objects.isNull(tbGoods.getGoodsId())) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        if (Objects.isNull(tbGoods.getGoodsStock())) {
            throw new IllegalArgumentException("商品库存不能为空");
        }
        return service.updateById(tbGoods);
    }

    @RequestMapping("findById/{id}")

    public Goods findById(@PathVariable Integer id) {

        System.out.println("id" + id);
        return new Goods(id, "小米", 99);
    }

}