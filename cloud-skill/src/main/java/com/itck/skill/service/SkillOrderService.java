package com.itck.skill.service;

import com.itck.entity.domain.R;
import com.itck.skill.dto.SkillOrderAddDto;

public interface SkillOrderService {
    //秒杀
    //事务
    R save(SkillOrderAddDto dto, int uid);

    R save2(SkillOrderAddDto dto, int uid, String sign) throws InterruptedException;
}
