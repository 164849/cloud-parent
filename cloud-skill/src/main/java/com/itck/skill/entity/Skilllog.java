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
 * @Date: 2022/5/20 11:02
 */
@Data
@TableName("t_skilllog")
@NoArgsConstructor

public class Skilllog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 秒杀商品ID
     */
    private Integer sgid;
    /**
     * 秒杀结果 1.成功 2.失败
     */
    private String status;
    /**
     * 创建时间
     */
    private Date ctime;

    public Skilllog(Integer uid, Integer sgid, String status) {
        this.uid = uid;
        this.sgid = sgid;
        this.status = status;
        this.ctime = new Date();
    }
}

