package org.itck.orders.api;

import com.itck.entity.ddd.Goods;
import com.itck.entity.domain.R;
import com.itck.entity.dto.GoodsDto;
import com.itck.entity.dto.GoodsStockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("cloud-goodsddd")
@RequestMapping("/service/goods")
public interface GoodsProvider {

    //查询商品
    @GetMapping("single")
    R<GoodsDto> single(@RequestParam("id") int id);

    //更改库存
    @PostMapping("update")
    R update(@RequestBody GoodsStockDto dto);
}
