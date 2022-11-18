package com.itck.skill.controller;

import com.itck.entity.domain.R;
import com.itck.skill.dto.SkillActivityAddDto;
import com.itck.skill.dto.SkillActivityAuditDto;
import com.itck.skill.service.SkillaAtivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * 秒杀活动控制器
 *
 * @author zed
 * @date 2022/11/14
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("skill")
public class SkillActivityController {

    private final SkillaAtivityService ativityService;

    /**
     * 新增秒杀活动
     *
     * @param dto dto
     * @return {@link R}
     */
    @PostMapping("sava")
    public R save(@RequestBody SkillActivityAddDto dto) {
        // 参数校验 Assert
        return ativityService.save(dto);
    }

    /**
     * 查询列表
     *
     * @param flag 国旗
     * @return {@link R}
     */
    @GetMapping("query/{flag}")
    public R queryList(@PathVariable("flag") Integer flag) {
        Assert.isTrue(flag >= 0, "活动状态必须是大于等于0的整数");
        return ativityService.queryList(flag);
    }

    /**
     * 修改活动状态
     *
     * @param dto dto
     * @return {@link R}
     */
    @PostMapping("change")
    public R change(@RequestBody SkillActivityAuditDto dto) {
        Assert.notNull(dto.getId(), "ID不能为空");
        Assert.notNull(dto.getFlag(), "状态不能为空");
        return ativityService.change(dto);
    }

    /**
     * 查询秒杀活动倒计时
     *
     * @param id id
     * @return {@link R}
     */
    @PostMapping("time/{id}")
    public R queryTime(@PathVariable("id") Integer id) {
        Assert.notNull(id, "ID不能为空");
        return ativityService.queryTime(id);
    }

}
