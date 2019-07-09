package com.cskaoyan.bean.wx.pagedata;

public class BrandPageData<T> {
    T brandList;
    long totalPages;

    public BrandPageData() {
    }

    public BrandPageData(T brandList, long totalPages) {
        this.brandList = brandList;
        this.totalPages = totalPages;
    }

    public T getBrandList() {
        return brandList;
    }

    public void setBrandList(T brandList) {
        this.brandList = brandList;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
