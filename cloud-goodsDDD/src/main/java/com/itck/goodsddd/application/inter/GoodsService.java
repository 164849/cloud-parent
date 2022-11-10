package com.itck.goodsddd.application.inter;

import com.itck.entity.domain.R;
import com.itck.entity.dto.GoodsDto;
import com.itck.entity.dto.GoodsStockDto;
import com.itck.entity.ddd.Goods;

public interface GoodsService {

    R findAll();

    R<GoodsDto> findById(Integer id);

    R updateStock(GoodsStockDto dto);

}
