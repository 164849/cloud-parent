package com.itck.skill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itck.skill.entity.Skillorder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SkillOrderDao extends BaseMapper<Skillorder> {

    @Update("update t_skillorder set flag=#{flag} where id=#{id}")
    int updateFlag(@Param("id") int id, @Param("flag") int flag);

}
