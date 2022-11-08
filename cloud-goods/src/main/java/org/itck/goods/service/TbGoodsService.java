package org.itck.goods.service;

import com.itck.entity.domain.R;
import com.itck.entity.domain.TbGoods;

public interface TbGoodsService {

    R<TbGoods> findById(Integer id);

    R updateById(TbGoods tbGoods);
}
