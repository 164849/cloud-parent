package com.itck.skill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description:
 * @Author: zed
 * @Date: 2022/5/20 11:00
 */
@Data
@TableName("t_skillorderlog")
@NoArgsConstructor
public class Skillorderlog {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 秒杀订单id
     */
    private Integer oid;
    /**
     * 状态
     */
    private Integer type;
    /**
     * 备注信息
     */
    private String info;
    /**
     * 更新时间
     */
    private Date ctime;

    public Skillorderlog(Integer oid, Integer type, String info) {
        this.oid = oid;
        this.type = type;
        this.info = info;
        this.ctime = new Date();
    }
}
