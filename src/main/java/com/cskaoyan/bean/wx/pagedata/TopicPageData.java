package com.cskaoyan.bean.wx.pagedata;

public class TopicPageData<T> {
    T data;
    long count;

    public TopicPageData() {
    }

    public TopicPageData(T data, long count) {
        this.data = data;
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
