package org.itck.orders.mapper;

import org.itck.orders.entity.TOrderlog;
import org.apache.ibatis.annotations.Param;


import java.awt.print.Pageable;
import java.util.List;

/**
 * 15.订单状态变化表(TOrderlog)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-10 15:03:18
 */
public interface TOrderlogMapper {

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
     * @return 影响行数
     */
    int insert(TOrderlog tOrderlog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TOrderlog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TOrderlog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TOrderlog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TOrderlog> entities);

    /**
     * 修改数据
     *
     * @param tOrderlog 实例对象
     * @return 影响行数
     */
    int update(TOrderlog tOrderlog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

