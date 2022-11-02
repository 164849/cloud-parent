package org.itck.orders.api;

import com.itck.entity.domain.Jifen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("cloud-jifen")
@RequestMapping("/jifen")
public interface JifenApi {

    @PostMapping(value = "/save")
    Map save(@RequestBody Jifen jifen);
}