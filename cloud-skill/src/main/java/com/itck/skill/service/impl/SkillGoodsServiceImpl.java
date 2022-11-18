package com.itck.skill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itck.common.utils.RedissonUtils;
import com.itck.entity.domain.R;
import com.itck.skill.config.RedisKeyConfig;
import com.itck.skill.config.SystemConfig;
import com.itck.skill.dao.SkillGoodsDao;
import com.itck.skill.dto.SkillGoodsAddDto;
import com.itck.skill.dto.SkillGoodsDetailDto;
import com.itck.skill.dto.SkillGoodsDto;
import com.itck.skill.entity.Skillgoods;
import com.itck.skill.service.SkillGoodsService;
import com.itck.skill.utils.FreeMarkerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 秒杀商品
 *
 * @author zed
 * @date 2022/11/14
 */
@Service
@RequiredArgsConstructor
public class SkillGoodsServiceImpl implements SkillGoodsService {


    // spring 的 bean 既然是单例的 只有一个实例 怎么保证线程安全？
    // bean中注入的对象 只有接口 并且是final修饰的，不可改变的所以是线程安全
    private final SkillGoodsDao dao;

    // 新增秒杀商品
    @Override
    @Transactional
    public R save(SkillGoodsAddDto dto) {
        Skillgoods skillgoods = new Skillgoods();
        BeanUtils.copyProperties(dto, skillgoods);
        if (dao.insert(skillgoods) > 0) {
            return R.ok();
        } else {
            return R.fail();
        }
    }


    /**
     * 内部人员使用
     * 秒杀商品上架
     * 1、修改商品状态
     * 2、生成静态页面 web/webview/H5的页面套壳 打包APP
     * 3、设置活动商品库存量的缓存
     *
     * @param dto dto
     * @return {@link R}
     */
    @Override
    @Transactional
    public R up(SkillGoodsDto dto) {
        //1.验证是否位数商品上架
        if (dto.getFlag() == SystemConfig.GOODS_UP) {
            //2.修改状态和静态路径
            //如果是商品上架，生成静态页面 goods1.html
            if (dao.updateFlagUrl(dto.getFlag(),
                    SystemConfig.GOODS_DETAIL_PRE + dto.getId() + ".html", dto.getId()) > 0) {

                //动态页面静态化-FreeMarker
                //3.查询详细信息
                SkillGoodsDetailDto detailDto = dao.selectByGid(dto.getId());
                //4.通过FreeMarker 生成静态页面 直接生成(推荐) 、MQ 异步生成
                FreeMarkerUtil.createHtml(detailDto);

                //5.实现商品缓存
                // hash数据结构 key:秒杀活动前缀+ID  field:秒杀商品ID  value:商品库存数
                String key = RedisKeyConfig.SKILL_GOODS + detailDto.getSaid();
                if (RedissonUtils.checkKey(key)) {
                    //直接缓存本商品
                    RedissonUtils.setHash(key, detailDto.getSgid() + "", detailDto.getStock());
                } else {
                    //缓存商品
                    RedissonUtils.setHash(key, detailDto.getSgid() + "", detailDto.getStock());
                    //设置有效期,秒杀活动中的商品key的过期时间应该跟秒杀活动的过期时间一直
                    // ttl: 返回的过期时间是一个毫秒值 expire：设置过期时间时是秒的单位
                    RedissonUtils.expire(key, RedissonUtils.ttl(RedisKeyConfig.SKILL_ACTIVITY + detailDto.getSaid()) / 1000);
                }
                //6.返回
                return R.ok();
            } else {
                return R.fail();
            }
        } else {
            if (dao.updateFlag(dto) > 0) {
                return R.ok();
            } else {
                return R.fail();
            }
        }
    }


    /**
     * 查询秒杀商品
     *
     * @param said skill activity 秒杀活动ID
     * @return {@link R} 这个活动中的秒杀商品列表
     */
    @Override
    public R queryList(int said) {
        QueryWrapper<Skillgoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("said", said);
        queryWrapper.orderByDesc("id");
        return R.ok(dao.selectList(queryWrapper));
    }
}
