package com.cskaoyan.bean.wx.pagedata;

import com.cskaoyan.bean.wx.order.WxOrder;

import java.util.List;

public class OrderListPageData<T> {
    private Integer count;
    T data;
    Integer totalPages;

    public OrderListPageData() {
    }

    public OrderListPageData(Integer count, T data, Integer totalPages) {
        this.count = count;
        this.data = data;
        this.totalPages = totalPages;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
