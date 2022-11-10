package org.itck.orders.service.impl;

import org.itck.orders.entity.TOrderlog;
import org.itck.orders.mapper.TOrderlogMapper;
import org.itck.orders.service.TOrderlogService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

/**
 * 15.订单状态变化表(TOrderlog)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 15:03:30
 */
@Service("tOrderlogService")
public class TOrderlogServiceImpl implements TOrderlogService {
    @Resource
    private TOrderlogMapper tOrderlogMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TOrderlog queryById(Integer id) {
        return this.tOrderlogMapper.queryById(id);
    }
    

    /**
     * 新增数据
     *
     * @param tOrderlog 实例对象
     * @return 实例对象
     */
    @Override
    public TOrderlog insert(TOrderlog tOrderlog) {
        this.tOrderlogMapper.insert(tOrderlog);
        return tOrderlog;
    }

    /**
     * 修改数据
     *
     * @param tOrderlog 实例对象
     * @return 实例对象
     */
    @Override
    public TOrderlog update(TOrderlog tOrderlog) {
        this.tOrderlogMapper.update(tOrderlog);
        return this.queryById(tOrderlog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tOrderlogMapper.deleteById(id) > 0;
    }
}
