package com.itck.coupon.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itck.common.utils.RedissonUtils;
import com.itck.common.utils.SnowFlowUtil;
import com.itck.coupon.bo.MqMsgBo;
import com.itck.coupon.config.CouponAudit;
import com.itck.coupon.config.RabbitMQConstConfig;
import com.itck.coupon.config.RedisKeyConfig;
import com.itck.coupon.config.SystemConfig;
import com.itck.coupon.dto.CouponAuditDto;
import com.itck.coupon.entity.TCouponTemplate;
import com.itck.coupon.entity.TUsercoupon;
import com.itck.coupon.mapper.TCouponTemplateDao;
import com.itck.coupon.mapper.TUsercouponDao;
import com.itck.coupon.service.CouponService;
import com.itck.entity.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final TCouponTemplateDao couponTemplateDao;

    private final RabbitTemplate rabbitTemplate;

    private final TUsercouponDao userCouponDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R save(TCouponTemplate template) {
        if (couponTemplateDao.insert(template) > 0) {
            return R.ok();
        }
        return R.fail("新增优惠券失败");
    }

    @Override
    public R<PageInfo<TCouponTemplate>> queryAll(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<TCouponTemplate> list = couponTemplateDao.findAll();
        PageInfo<TCouponTemplate> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R audit(CouponAuditDto dto) {
        //根据id查询优惠卷模板对象
        TCouponTemplate template = couponTemplateDao.selectByPrimaryKey(dto.getId());
        //判断是否为审核通过状态
        if (Objects.nonNull(template) && template.getFlag() == CouponAudit.未审核.getCode()) {
            //进行优惠卷审核,补全优惠券模板信息
            template.setUserId(dto.getAid());
            template.setFlag(dto.getFlag());
            template.setUserAudit(dto.getInfo());
            String rk = "";
            //更新优惠价模板
            if (couponTemplateDao.updateByPrimaryKeySelective(template) > 0) {
                //优惠券状态更新成功
                //根据优惠券审核状态不同，通过的话发放优惠券
                if (template.getFlag() == CouponAudit.审核通过.getCode()) {
                    // 使用MQ异步发送，如果流量过大直接操作数据库将是一个巨大的工程
                    MqMsgBo bo = new MqMsgBo();
                    bo.setId(SnowFlowUtil.getInstance().nextId());
                    bo.setData(template);
                    //MQ的路由key
                    if (template.getSendType() == SystemConfig.COUPON_SEND_SYSTEM) {
                        bo.setType(RabbitMQConstConfig.MQTYPE_COUPONSYS);
                        rk = RabbitMQConstConfig.RK_COUPONSYS;
                    } else if (template.getSendType() == SystemConfig.COUPON_SEND_USER) {
                        bo.setType(RabbitMQConstConfig.MQTYPE_COUPONUSE);
                        rk = RabbitMQConstConfig.RK_COUPONUSE;
                    }
                    //MQ发送消息
                    if (StringUtils.hasLength(rk)) {
                        rabbitTemplate.convertAndSend(RabbitMQConstConfig.EX_COUPONTEM, rk, bo);

                    }

                    return R.ok("审核完成");

                }

            }
        }
        return R.fail("亲，非法请求");
    }

    @Override
    @Transactional
    public R receive(Integer uid, Integer rl, Integer ctid) {

        boolean r = true;
        boolean isFirstTime = false;
        if (RedissonUtils.checkKey(RedisKeyConfig.COUPON_CACHE + ctid)) {
            if (RedissonUtils.checkKey(RedisKeyConfig.COUPON_USERS + ctid)) {

                if (RedissonUtils.exists(RedisKeyConfig.COUPON_USERS + ctid, uid + "")) {
                    r = false; // 说明用户领取过
                }

            } else {
                isFirstTime = true;// 有用户第一次来领取这个优惠券
            }
            if (r) {
                RLock lock = RedissonUtils.getLock(RedisKeyConfig.COUPON_LOCK + ctid);
                try {
                    if (lock.tryLock(10, TimeUnit.SECONDS)) {
                        int count = (int) RedissonUtils.getList(RedisKeyConfig.COUPON_CACHE + ctid, 0);
                        int level = (int) RedissonUtils.getList(RedisKeyConfig.COUPON_CACHE + ctid, 1);
                        if (count > 0) {
                            if (rl < level) {
                                return R.fail("抱歉，您的级别不够！");
                            } else {
                                if (userCouponDao.insert(new TUsercoupon(ctid, uid, "user_" + SnowFlowUtil.getInstance().nextId())) > 0) {
                                    // 更新缓存
                                    RedissonUtils.setList(RedisKeyConfig.COUPON_CACHE + ctid, 0, count - 1);
                                    RedissonUtils.setSet(RedisKeyConfig.COUPON_USERS + ctid, uid + "");
                                    // 如果是用户第一次领取某个模板的优惠券设置key的过期时间
                                    if (isFirstTime) {
                                        long ttl = RedissonUtils.ttl(RedisKeyConfig.COUPON_CACHE + ctid);
                                        RedissonUtils.expire(RedisKeyConfig.COUPON_USERS + ctid, ttl / 1000);
                                    }
                                    return R.ok();
                                }

                            }
                        } else {
                            return R.fail("亲，优惠券没有，下次早点来哟~");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return R.fail("系统出现异常");

                } finally {
                    lock.unlock();
                }
            } else {
                return R.fail("已经领过了，亲");
            }
        } else {
            return R.fail("亲，活动已结束！");
        }
        return R.fail("系统故障！");

    }
}
