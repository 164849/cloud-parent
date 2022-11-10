package com.itck.goodsddd.infra.repo;

import com.itck.entity.ddd.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsDao extends JpaRepository<Goods, Integer> {

    //查询 JQPL:面向对象查询语言 表->类
    @Query(value = "from Goods")
    List<Goods> selectAll();

    //方法名解析查询:按照约定，编写方法名，就会自动生成SQL语句
    Goods getById(int id);

    //模糊查询
    List<Goods> getByTitleLike(String title);

    //原生SQL语句
    @Modifying//执行DML类型
    @Query(value = "update t_goods set stock_num=stock_num+:num where id=:id", nativeQuery = true)
    int updateStock(int num, int id);
}
