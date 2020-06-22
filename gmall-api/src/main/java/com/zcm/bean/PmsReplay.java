package com.zcm.bean;
import java.io.Serializable;
import java.util.Date;

public class PmsReplay implements Serializable {
    private Integer id;

    private Integer commentId;

    private String memberNickName;

    private String memberIcon;

    private String content;

    private Date createTime;

    private String type;

    private Integer memberIdOne;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getMemberNickName() {
        return memberNickName;
    }

    public void setMemberNickName(String memberNickName) {
        this.memberNickName = memberNickName == null ? null : memberNickName.trim();
    }

    public String getMemberIcon() {
        return memberIcon;
    }

    public void setMemberIcon(String memberIcon) {
        this.memberIcon = memberIcon == null ? null : memberIcon.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getMemberIdOne() {
        return memberIdOne;
    }

    public void setMemberIdOne(Integer memberIdOne) {
        this.memberIdOne = memberIdOne;
    }
}
