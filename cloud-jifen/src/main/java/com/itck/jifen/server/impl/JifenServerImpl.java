package com.itck.jifen.server.impl;

import com.itck.entity.domain.Integral;
import com.itck.entity.domain.R;
import com.itck.jifen.mapper.JifenMapper;
import com.itck.jifen.server.JifenServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JifenServerImpl implements JifenServer {

    private final JifenMapper mapper;

    @Override
    public R saveJifen(Integral integral) {
        integral.setIntegral(10);
        if (Objects.isNull(integral.getOrderId())) {
            return R.fail("订单id不能为空");
        }
        mapper.saveJiFen(integral);
        return R.ok();
    }
}
