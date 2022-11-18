package com.itck.skill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.itck.skill.config.SystemConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: zed
 * @Date: 2022/5/20 10:53
 */
@Data
@TableName("t_skillactivity")
public class Skillactivity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String title;
    /**
     * 描述
     */
    private String info;
    /**
     * 活动图片
     */
    private String picurl;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date etime;
    /**
     * 状态
     */
    private Integer flag;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date ctime;
    /**
     * 最大购买量
     */
    private Integer maxcount;

    public Skillactivity() {
        this.ctime = new Date();
        this.flag = SystemConfig.ACTIVITY_ADD;
    }
}
