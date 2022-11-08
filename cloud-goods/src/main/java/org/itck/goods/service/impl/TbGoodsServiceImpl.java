package org.itck.goods.service.impl;

import com.itck.entity.domain.R;
import com.itck.entity.domain.TbGoods;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itck.goods.mapper.TbGoodsMapper;
import org.itck.goods.service.TbGoodsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TbGoodsServiceImpl implements TbGoodsService {

    private final TbGoodsMapper tbGoodsMapper;

    @Override
    public R<TbGoods> findById(Integer id) {
        return R.ok(tbGoodsMapper.findById(id));
    }

    @Override
    public R updateById(TbGoods tbGoods) {
        if (tbGoodsMapper.update(tbGoods.getGoodsId(), tbGoods.getGoodsStock()) > 0) {
            return R.ok();
        }
        return R.fail("更新失败");
    }
}
