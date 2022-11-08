package org.itck.orders.service;

import com.itck.entity.domain.R;
import com.itck.entity.domain.TbOrder;

public interface OrderService {

    R saveOrder(TbOrder order);
}
