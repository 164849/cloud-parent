package com.itck.skill.service;

import com.itck.entity.domain.R;
import com.itck.skill.dto.SkillGoodsAddDto;
import com.itck.skill.dto.SkillGoodsDto;
import org.springframework.transaction.annotation.Transactional;

public interface SkillGoodsService {
    /**
     * 新增秒杀商品
     *
     * @param dto dto
     * @return {@link R}
     */
    @Transactional
    R save(SkillGoodsAddDto dto);


    /**
     * 内部人员使用
     * 秒杀商品上架
     *
     * @param dto dto
     * @return {@link R}
     */
    @Transactional
    R up(SkillGoodsDto dto);

    /**
     * 查询列表
     *
     * @param said 说
     * @return {@link R}
     */
    R queryList(int said);
}
