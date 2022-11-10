package org.itck.orders.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 15.订单状态变化表(TOrderlog)实体类
 *
 * @author makejava
 * @since 2022-11-10 15:03:18
 */
public class TOrderlog implements Serializable {
    private static final long serialVersionUID = 798588828222766642L;

    private Integer id;

    private Integer oid;
    /**
     * 对应订单状态类型
     */
    private Integer type;
    /**
     * 内容
     */
    private String info;

    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public TOrderlog() {
    }

    public TOrderlog(Integer oid, Integer type, String info) {
        this.oid = oid;
        this.type = type;
        this.info = info;
        this.createTime = new Date();
    }


}

