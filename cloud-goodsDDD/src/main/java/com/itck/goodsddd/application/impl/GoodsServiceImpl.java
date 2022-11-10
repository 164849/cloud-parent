package com.itck.goodsddd.application.impl;

import com.itck.entity.domain.R;
import com.itck.entity.dto.GoodsDto;
import com.itck.entity.dto.GoodsStockDto;
import com.itck.goodsddd.application.inter.GoodsService;
import com.itck.entity.ddd.Goods;
import com.itck.goodsddd.infra.repo.GoodsDao;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsDao dao;

    @Override
    public R findAll() {
        return R.ok(dao.findAll());
    }

    @Override
    public R<GoodsDto> findById(Integer id) {

        Goods goods = dao.getById(id);
        GoodsDto dto = new GoodsDto();
        // 对象之间的属性值拷贝
        BeanUtils.copyProperties(goods, dto);
        return R.ok(dto);
    }

    @Override
    @Transactional
    @GlobalTransactional
    public R updateStock(GoodsStockDto dto) {
        if (dao.updateStock(dto.getNum(), dto.getId()) > 0) {
            return R.ok();
        }
        return R.fail("更新库存失败");
    }
}
