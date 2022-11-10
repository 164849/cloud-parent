package com.itck.goodsddd.gate.rest;

import com.itck.entity.domain.R;
import com.itck.entity.dto.GoodsDto;
import com.itck.entity.dto.GoodsStockDto;
import com.itck.goodsddd.application.inter.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 产品控制器
 *
 * @author zed
 * @date 2022/11/10
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/service/goods")
public class GoodsController {

    private final GoodsService service;

    @GetMapping("all")
    public R all() {
        return service.findAll();
    }

    @GetMapping("single")
    public R<GoodsDto> single(@RequestParam int id) {
        return service.findById(id);
    }

    @PostMapping("update")
    public R update(@RequestBody GoodsStockDto dto) {
        return service.updateStock(dto);
    }

}
