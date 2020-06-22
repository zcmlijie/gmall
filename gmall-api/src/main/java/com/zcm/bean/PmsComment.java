package com.zcm.bean;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PmsComment implements Serializable {
    private Integer id;

    private Integer productId;

    private String memberNickName;

    private String productName;

    private String star;

    private String memberIp;

    private Date createTime;

    private String showStatus;

    private String productAttribute;

    private Integer collectCount;

    private Integer readCount;

    private String pics;

    private String memberIcon;

    private Integer replayCount;

    private String content;
    @Transient
    private List<PmsReplay> replayList;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getMemberNickName() {
        return memberNickName;
    }

    public void setMemberNickName(String memberNickName) {
        this.memberNickName = memberNickName == null ? null : memberNickName.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star == null ? null : star.trim();
    }

    public String getMemberIp() {
        return memberIp;
    }

    public void setMemberIp(String memberIp) {
        this.memberIp = memberIp == null ? null : memberIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus == null ? null : showStatus.trim();
    }

    public String getProductAttribute() {
        return productAttribute;
    }

    public void setProductAttribute(String productAttribute) {
        this.productAttribute = productAttribute == null ? null : productAttribute.trim();
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics == null ? null : pics.trim();
    }

    public String getMemberIcon() {
        return memberIcon;
    }

    public void setMemberIcon(String memberIcon) {
        this.memberIcon = memberIcon == null ? null : memberIcon.trim();
    }

    public Integer getReplayCount() {
        return replayCount;
    }

    public void setReplayCount(Integer replayCount) {
        this.replayCount = replayCount;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public List<PmsReplay> getReplayList() {
        return replayList;
    }

    public void setReplayList(List<PmsReplay> replayList) {
        this.replayList = replayList;
    }
}
