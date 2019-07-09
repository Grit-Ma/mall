package com.cskaoyan.bean.wx.pagedata;

/**
 * created by ZengWei
 * on 2019/7/9
 */
public class CouponPageData<T> {
    T data;
    long count;

    public CouponPageData() {
    }

    public CouponPageData(T data, long count) {
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
