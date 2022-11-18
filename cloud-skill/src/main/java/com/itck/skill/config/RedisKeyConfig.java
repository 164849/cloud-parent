package com.itck.skill.config;

public interface RedisKeyConfig {
    // 未开始的秒杀活动
    String SKILL_ACTIVITY_NOSTART = "skill:activity:nostart:";
    // 进行中的秒杀活动
    String SKILL_ACTIVITY = "skill:activity:start:";

    String SKILL_GOODS = "skill:goods:";

    String SKILL_ACTIVITY_LOCK = "skill:activity:lock:";
}
