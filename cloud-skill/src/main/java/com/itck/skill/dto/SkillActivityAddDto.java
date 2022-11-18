package com.itck.skill.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 秒杀活动对象
 * @Author: zed
 * @Date: 2022/5/20 11:10
 */
@Data
public class SkillActivityAddDto {
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
     * 最大购买量
     */
    private Integer maxcount;
}
