package com.itck.skill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itck.skill.dto.SkillActivityAuditDto;
import com.itck.skill.entity.Skillactivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @Description:
 * @Author: zed
 * @Date: 2022/5/20 11:03
 */
@Mapper
public interface SkillActivityDao extends BaseMapper<Skillactivity> {
    @Update("update t_skillactivity set flag=#{flag} where id=#{id}")
    int updateFlag(SkillActivityAuditDto dto);
}
