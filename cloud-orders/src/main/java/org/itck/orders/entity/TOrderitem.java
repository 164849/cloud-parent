package org.itck.orders.entity;

import java.io.Serializable;

/**
 * 14.订单详情表(TOrderitem)实体类
 *
 * @author zed
 * @since 2022-11-10 17:00:21
 */
public class TOrderitem implements Serializable {
    private static final long serialVersionUID = 924537757965861389L;

    private Integer id;

    private Integer oid;

    private Integer gid;
    /**
     * 价格
     */
    private Double price;

    private Integer num;


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

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public TOrderitem() {
    }

    public TOrderitem(Integer oid, Integer gid, Double price, Integer num) {
        this.oid = oid;
        this.gid = gid;
        this.price = price;
        this.num = num;
    }
}
