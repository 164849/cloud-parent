package com.itck.skill.dto;

import lombok.Data;

/**
 * @Description:
 * @Author: zed
 * @Date: 2022/5/20 11:10
 */
@Data
public class SkillActivityAuditDto {
    // 秒杀活动的ID
    private Integer id;
    // 秒杀活动的状态
    private Integer flag;
}
