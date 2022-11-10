package org.itck.orders.service;

import org.itck.orders.entity.TOrderlog;


/**
 * 15.订单状态变化表(TOrderlog)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 15:03:19
 */
public interface TOrderlogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TOrderlog queryById(Integer id);


    /**
     * 新增数据
     *
     * @param tOrderlog 实例对象
     * @return 实例对象
     */
    TOrderlog insert(TOrderlog tOrderlog);

    /**
     * 修改数据
     *
     * @param tOrderlog 实例对象
     * @return 实例对象
     */
    TOrderlog update(TOrderlog tOrderlog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
