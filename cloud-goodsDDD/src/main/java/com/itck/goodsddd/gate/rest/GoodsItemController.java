package com.itck.goodsddd.gate.rest;

import com.itck.entity.domain.R;
import com.itck.goodsddd.application.inter.GoodsItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server/goodsitem/")
@RequiredArgsConstructor
public class GoodsItemController {

    private final GoodsItemService service;

    @GetMapping("all")
    public R all() {
        return service.findAll();
    }
}
