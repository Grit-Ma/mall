package com.cskaoyan.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class GoodsCommentVo {

    private String content;
    private String[] picList;
    private CommentUserInfo userInfo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getPicList() {
        return picList;
    }

    public void setPicList(String[] picList) {
        this.picList = picList;
    }

    public CommentUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(CommentUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
