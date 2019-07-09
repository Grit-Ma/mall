package com.cskaoyan.bean.wx.pagedata;

public class CollectPageData<T> {
    T collectdList;
    long totalPages;

    public CollectPageData() {
    }

    public CollectPageData(T collectdList, long totalPages) {
        this.collectdList = collectdList;
        this.totalPages = totalPages;
    }

    public T getCollectdList() {
        return collectdList;
    }

    public void setCollectdList(T collectdList) {
        this.collectdList = collectdList;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
