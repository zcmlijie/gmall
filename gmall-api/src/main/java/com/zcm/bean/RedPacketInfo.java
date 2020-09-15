package com.zcm.bean;

import java.io.Serializable;
import java.util.Date;

public class RedPacketInfo implements Serializable {
    private Integer id;//

    private Long redPacketId;//红包id

    private Integer totalAmount;//红包总⾦额，单位分

    private Integer totalPacket;//红包总个数

    private Integer remainingAmount;//剩余红包⾦额

    private Integer remainingPacket;//剩余红包个数

    private Integer uid;//新建红包⽤户的⽤户标识

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalPacket() {
        return totalPacket;
    }

    public void setTotalPacket(Integer totalPacket) {
        this.totalPacket = totalPacket;
    }

    public Integer getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Integer remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public Integer getRemainingPacket() {
        return remainingPacket;
    }

    public void setRemainingPacket(Integer remainingPacket) {
        this.remainingPacket = remainingPacket;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
