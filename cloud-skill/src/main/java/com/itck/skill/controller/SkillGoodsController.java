package com.itck.skill.controller;

import com.itck.entity.domain.R;
import com.itck.skill.dto.SkillGoodsAddDto;
import com.itck.skill.dto.SkillGoodsDto;
import com.itck.skill.service.SkillGoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("skill/goods")
public class SkillGoodsController {

    private final SkillGoodsService goodsService;

    /**
     * 新增秒杀商品
     *
     * @param dto dto 秒杀商品对象
     * @return {@link R}
     */
    @PostMapping("save")
    public R save(@RequestBody SkillGoodsAddDto dto) {
        return goodsService.save(dto);
    }


    /**
     * 秒杀商品上架
     *
     * @param dto dto
     * @return {@link R}
     */
    @PostMapping("up")
    public R up(@RequestBody SkillGoodsDto dto) {
        Assert.notNull(dto.getId(), "秒杀商品ID不能为空");
        Assert.notNull(dto.getFlag(), "秒杀商品状态不能为空");
        return goodsService.up(dto);
    }

    /**
     * 查询某个秒杀活动下的商品列表
     *
     * @param said 秒杀活动ID
     * @return {@link R}
     */
    @GetMapping("list")
    public R queryList(@RequestParam int said) {
        Assert.notNull(said, "秒杀活动ID不能为空");
        return goodsService.queryList(said);
    }
}
