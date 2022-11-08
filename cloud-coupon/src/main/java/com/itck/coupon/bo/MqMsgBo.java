package com.itck.coupon.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqMsgBo implements Serializable {
    private long id;//唯一id,防止消息重复,雪花算法
    private int type;//类型
    private Object data;//消息内容
}
