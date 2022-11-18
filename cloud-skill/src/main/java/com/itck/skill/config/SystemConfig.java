package com.itck.skill.config;

/**
 * @Description:
 * @Author: zed
 * @Date: 2022/5/20 11:19
 */
public interface SystemConfig {
    //秒杀活动
    int ACTIVITY_ADD = 201;//未审核
    int ACTIVITY_SUCCESS = 202;//审核成功
    int ACTIVITY_FAIL = 203;//审核失败
    int ACTIVITY_DEL = 204;//删除

    //秒杀商品
    int GOODS_ADD = 211;//新增
    int GOODS_SUCCESS = 212;//审核成功
    int GOODS_FAIL = 213;//审核失败
    int GOODS_UP = 214;//上架
    int GOODS_DOWN = 215;//下架
    int GOODS_DEL = 216;//删除

    //商品详情页的前缀
    String GOODS_DETAIL_PRE = "goods";

    //秒杀订单的状态
    int ORDER_ADD = 221;//未付款
    int ORDER_PAYED = 222;//已付款
    int ORDER_CONFIRM = 223;//未确认，已发货
    int ORDER_NOCOMMENT = 224;//未评价
    int ORDER_COMMENTED = 225;//已评价
    int ORDER_TIMEOUT = 226;//超时
    int ORDER_CANCEL = 227;//取消

    //秒杀订单超时
    int SKILL_ORDER_TIMEOUT = 15 * 60000;//15分钟

    //令牌桶 的每秒的令牌数量
    int SKILL_TOKENS = 200;

}
