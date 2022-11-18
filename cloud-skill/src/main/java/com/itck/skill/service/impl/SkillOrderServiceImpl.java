package com.itck.skill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itck.common.config.RabbitMQConstConfig;
import com.itck.common.utils.RedissonUtils;
import com.itck.common.utils.SnowFlowUtil;
import com.itck.entity.bo.MqMsgBo;
import com.itck.entity.domain.R;
import com.itck.skill.config.RedisKeyConfig;
import com.itck.skill.config.SystemConfig;
import com.itck.skill.dao.SkillGoodsDao;
import com.itck.skill.dao.SkillLogDao;
import com.itck.skill.dao.SkillOrderDao;
import com.itck.skill.dao.SkillOrderLogDao;
import com.itck.skill.dto.SkillGoodsDetailDto;
import com.itck.skill.dto.SkillOrderAddDto;
import com.itck.skill.entity.Skilllog;
import com.itck.skill.entity.Skillorder;
import com.itck.skill.entity.Skillorderlog;
import com.itck.skill.service.SkillOrderService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SkillOrderServiceImpl implements SkillOrderService {
    private final SkillOrderDao dao;
    private final SkillGoodsDao skillgoodsDao;
    private final SkillLogDao skilllogDao;
    private final SkillOrderLogDao skillorderlogDao;
    private final RabbitTemplate rabbitTemplate;


    /**
     * Review 评审
     * 1.操作直接落到Mysql
     * 2.锁，无法保证超卖
     * 3.Redis
     * 4.MQ
     * 5.幂等性的问题：操作多次，结果一样，新增接口 需要解决幂等性的问题
     * 解决方案：
     * 1.前端  点击之后，立即把按钮不可使用
     * 2.锁
     * 3.Redis 性能
     */
    //秒杀
    @Override
    @Transactional //事务
    public R save(SkillOrderAddDto dto, int uid) {
        //秒杀实现--下单
        //1.查询秒杀商品的商品信息
//        Lock lock=new ReentrantLock();//通过Lock 解决超卖的问题
//        lock.lock();
        RLock rLock = RedissonUtils.getLock(RedisKeyConfig.SKILL_ACTIVITY_LOCK + dto.getSgid());
        try {
            //分布式锁  设置超时时间 防止意外情况的锁无法释放问题
            if (rLock.tryLock(6, TimeUnit.SECONDS)) {
                SkillGoodsDetailDto detailDto = skillgoodsDao.selectByGid(dto.getSgid());
                if (detailDto != null) {

                    //2.验证秒杀活动没有结束
                    Date date = new Date();
                    if (detailDto.getStime().getTime() < date.getTime() && detailDto.getEtime().getTime() > date.getTime()) {

                        //3.验证购买的数量是否超出约定的上限
                        if (detailDto.getMaxcount() >= dto.getNum()) {

                            //4.验证用户是否已经购买过
                            Skillorder skillorder = dao.selectOne(new QueryWrapper<Skillorder>().eq("uid", uid).eq("sgid", dto.getSgid()));
                            if (skillorder == null) {
                                //用户没有购买过，可以购买
                                //5.验证库存
                                if (detailDto.getStock() >= dto.getNum()) {
                                    //库存够，可以下单
                                    //6.生成订单信息
                                    Skillorder order = new Skillorder(SnowFlowUtil.getInstance().nextId() + "",
                                            dto.getSgid(), detailDto.getCurrprice(), dto.getNum(), dto.getUaid(), uid);
                                    if (dao.insert(order) > 0) {
                                        //7.扣减库存
                                        skillgoodsDao.updateStock(dto.getSgid(), -dto.getNum());

                                        //8.秒杀成功-记录
                                        skillorderlogDao.insert(new Skillorderlog(order.getId(), SystemConfig.ORDER_ADD, "秒杀订单生成！"));
                                        skilllogDao.insert(new Skilllog(uid, dto.getSgid(), "1"));//记录秒杀结果

                                        //9.返回
                                        return R.ok();
                                    } else {
                                        skilllogDao.insert(new Skilllog(uid, dto.getSgid(), "2"));//记录秒杀结果
                                        return R.fail("下单失败！");
                                    }
                                } else {
                                    return R.fail("亲，秒杀商品已卖完！");
                                }
                            } else {
                                //买过，本次不允许
                                return R.fail("亲，你已经买过了！");
                            }
                        } else {
                            return R.fail("亲，超出规定的购买上限！");
                        }
                    } else {
                        return R.fail("亲，秒杀活动已结束！");
                    }
                } else {
                    return R.fail("亲，秒杀商品无效！");
                }
            }
            rLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.fail("亲，秒杀已结束！");
    }


    /**
     * 第二版本秒杀下单
     * 分布式锁
     * redis
     * mq
     *
     * @param dto  dto
     * @param uid  uid
     * @param sign 标志
     * @return {@link R}
     */
    @Override
    @Transactional
    public R save2(SkillOrderAddDto dto, int uid, String sign) throws InterruptedException {
        //1.加锁 分布式锁
        RLock rLock = RedissonUtils.getLock(RedisKeyConfig.SKILL_ACTIVITY_LOCK + dto.getSgid());
        try {
            if (rLock.tryLock(6, TimeUnit.SECONDS)) {
                //校验库存
                String key = RedisKeyConfig.SKILL_GOODS + dto.getSaid();
                if (RedissonUtils.existsField(key, dto.getSgid() + "")) {
                    int n = (int) RedissonUtils.getHash(key, dto.getSgid() + "");
                    if (n >= dto.getNum()) {

                        //扣减库存,DB和Cache双写一致问题
                        if (skillgoodsDao.updateStock(dto.getSgid(), -dto.getNum()) > 0) {
                            RedissonUtils.setHash(key, dto.getSgid() + "", n - dto.getNum());

                            //生成订单信息
                            Skillorder order = new Skillorder(SnowFlowUtil.getInstance().nextId() + "",
                                    dto.getSgid(), dto.getPrice(), dto.getNum(), dto.getUaid(), uid);

                            //发送MQ消息，在MQ的消费者中异步下单
                            rabbitTemplate.convertAndSend(new MqMsgBo(SnowFlowUtil.getInstance().nextId(),
                                    RabbitMQConstConfig.MQTYPE_SKILLORDERADD, order));

                            return R.ok();
                        }
                    } else {
                        return R.fail("亲，已售完！");
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        } finally {
            rLock.unlock();
        }
        return R.fail("亲，秒杀已结束！");
    }
}
