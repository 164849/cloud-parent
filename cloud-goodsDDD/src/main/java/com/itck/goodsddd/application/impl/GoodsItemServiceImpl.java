package com.itck.goodsddd.application.impl;

import com.itck.entity.domain.R;
import com.itck.goodsddd.application.inter.GoodsItemService;
import com.itck.entity.ddd.GoodsItem;
import com.itck.goodsddd.infra.repo.GoodsItemDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsItemServiceImpl implements GoodsItemService {

    private final GoodsItemDao dao;

    @Override
    public R<List<GoodsItem>> findAll() {
        log.info("查询所有商品类型");
        return R.ok(dao.findAll());
    }
}
