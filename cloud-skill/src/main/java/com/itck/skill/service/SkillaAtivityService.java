package com.itck.skill.service;

import com.itck.entity.domain.R;
import com.itck.skill.dto.SkillActivityAddDto;
import com.itck.skill.dto.SkillActivityAuditDto;

public interface SkillaAtivityService {
    R save(SkillActivityAddDto dto);

    //内部 -
    R change(SkillActivityAuditDto dto);

    //用户-只能看审核通过活动 flag:0.查询全部秒杀活动 1.未开始活动 2.进行中活动 3.结束的活动
    R queryList(int flag);

    //秒杀活动开始的倒计时
    R queryTime(int id);
}
