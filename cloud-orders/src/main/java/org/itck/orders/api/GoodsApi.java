package org.itck.orders.api;


import com.itck.entity.domain.R;
import com.itck.entity.domain.TbGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("cloud-goods")
@RequestMapping("goods")
public interface GoodsApi {

    @GetMapping("quireById")
    R<TbGoods> quireById(@RequestParam("id") Integer id);

    @PostMapping("updateById")
    R<TbGoods> updateById(@RequestBody TbGoods tbGoods);
}
