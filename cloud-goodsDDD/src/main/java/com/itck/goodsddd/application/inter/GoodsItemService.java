package com.itck.goodsddd.application.inter;

import com.itck.entity.domain.R;
import com.itck.entity.ddd.GoodsItem;

import java.util.List;

public interface GoodsItemService {

    R<List<GoodsItem>> findAll();
}
