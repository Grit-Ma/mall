package com.cskaoyan.bean.wx.pagedata;

public class CollectPageData<T> {
    T collectList;
    long totalPages;

    public CollectPageData() {
    }

    public CollectPageData(T collectList, long totalPages) {
        this.collectList = collectList;
        this.totalPages = totalPages;
    }

    public T getCollectList() {
        return collectList;
    }

    public void setCollectList(T collectList) {
        this.collectList = collectList;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
