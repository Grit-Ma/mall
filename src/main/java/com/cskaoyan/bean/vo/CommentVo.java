package com.cskaoyan.bean.vo;

import com.cskaoyan.bean.Comment;

import java.util.List;

public class CommentVo {
    private int count;
    private List<CommentData> data;

    public CommentVo() {
    }

    public CommentVo(int count, List<CommentData> data) {
        this.count = count;
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommentData> getData() {
        return data;
    }

    public void setData(List<CommentData> data) {
        this.data = data;
    }
}
