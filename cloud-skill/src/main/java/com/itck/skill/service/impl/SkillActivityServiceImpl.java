package com.itck.skill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itck.common.utils.RedissonUtils;
import com.itck.entity.domain.R;
import com.itck.skill.config.RedisKeyConfig;
import com.itck.skill.config.SystemConfig;
import com.itck.skill.dao.SkillActivityDao;
import com.itck.skill.dto.SkillActivityAddDto;
import com.itck.skill.dto.SkillActivityAuditDto;
import com.itck.skill.entity.Skillactivity;
import com.itck.skill.service.SkillaAtivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SkillActivityServiceImpl implements SkillaAtivityService {


    private final SkillActivityDao dao;


    /**
     * 新增秒杀活动
     *
     * @param dto dto 秒杀活动传输对象
     * @return {@link R}
     */
    @Override
    @Transactional
    public R save(SkillActivityAddDto dto) {
        Skillactivity skillactivity = new Skillactivity();
        // 对象拷贝
        BeanUtils.copyProperties(dto, skillactivity);
        if (dao.insert(skillactivity) > 0) {
            return R.ok();
        } else {
            return R.fail();
        }
    }


    //内部 - 运营/管理人员使用的接口
    @Override
    @Transactional
    public R change(SkillActivityAuditDto dto) {
        // 修改秒杀活动状态
        if (dao.updateFlag(dto) > 0) {
            //审核成功-缓存活动
            if (dto.getFlag() == SystemConfig.ACTIVITY_SUCCESS) {
                // 把审核通过的秒杀活动放入redis中 缓存2个key
                Skillactivity skillactivity = dao.selectById(dto.getId());
                Date date = new Date();
                // 未开始的秒杀活动缓存key的过期时间 秒的时间
                long s = (skillactivity.getStime().getTime() - date.getTime()) / 1000;
                // 进行中的秒杀活动缓存key的过期时间 秒的时间
                long e = (skillactivity.getEtime().getTime() - date.getTime()) / 1000;

                //用来解决活动是否开始：未开始的秒杀活动
                RedissonUtils.setStr(RedisKeyConfig.SKILL_ACTIVITY_NOSTART + dto.getId(), 1, s);
                //用来缓存秒杀活动信息：进行中的秒杀活动
                RedissonUtils.setStr(RedisKeyConfig.SKILL_ACTIVITY + dto.getId(), skillactivity, e);

            } else if (dto.getFlag() == SystemConfig.ACTIVITY_DEL) {
                //删除了
                RedissonUtils.delKey(RedisKeyConfig.SKILL_ACTIVITY_NOSTART + dto.getId(), RedisKeyConfig.SKILL_ACTIVITY + dto.getId());
            }
            return R.ok();
        } else {
            return R.fail();
        }
    }


    //用户-只能看审核通过活动 flag:0.查询全部秒杀活动 1.未开始活动 2.进行中活动 3.结束的活动
    @Override
    public R queryList(int flag) {
        // mybatis-plus 构造查询条件 QueryWrapper
        QueryWrapper<Skillactivity> queryWrapper = new QueryWrapper<>();
        // select * from t_skillactivity where flag=202
        queryWrapper.eq("flag", SystemConfig.ACTIVITY_SUCCESS);
        if (flag > 0) {
            //查询某些状态
            switch (flag) {
                case 1://未开始活动 gt -- greater than 大于 / ge -- greater equal 大于等于
                    // and stime > 当期时间
                    queryWrapper.gt("stime", new Date());
                    break;
                case 2://2.进行中活动
                    // and stime < 当期时间 and etime > 当期时间
                    queryWrapper.lt("stime", new Date()).gt("etime", new Date());
                    break;
                case 3://3.结束的活动
                    // etime < 当期时间
                    queryWrapper.lt("etime", new Date());
                    break;
            }
        }
        queryWrapper.orderByDesc("id");
        return R.ok(dao.selectList(queryWrapper));
    }


    // 秒杀活动开始的倒计时 id:秒杀活动ID
    @Override
    public R queryTime(int id) {
        //活动开始的剩余时间
        if (RedissonUtils.checkKey(RedisKeyConfig.SKILL_ACTIVITY_NOSTART + id)) {
            return R.ok(RedissonUtils.ttl(RedisKeyConfig.SKILL_ACTIVITY_NOSTART + id));
        } else {
            if (RedissonUtils.checkKey(RedisKeyConfig.SKILL_ACTIVITY + id)) {
                return R.fail("秒杀活动进行中");
            }
        }
        return R.fail("秒杀活动不存在");
    }
}
