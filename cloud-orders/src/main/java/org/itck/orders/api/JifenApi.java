package org.itck.orders.api;

import com.itck.entity.domain.Integral;
import com.itck.entity.domain.Jifen;
import com.itck.entity.domain.R;
import com.itck.entity.domain.TbGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("cloud-jifen")
@RequestMapping("/jifen")
public interface JifenApi {

    @PostMapping(value = "/save")
    R save(@RequestBody Integral integral);


}