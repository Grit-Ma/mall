package com.cskaoyan.bean.vo;

import com.cskaoyan.bean.Comment;

import java.util.List;

public class CommentVo {
    private int count;
    private List<Comment> data;

    public CommentVo() {
    }

    public CommentVo(int count, List<Comment> data) {
        this.count = count;
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }
}
