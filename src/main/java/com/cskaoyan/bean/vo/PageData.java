package com.cskaoyan.bean.vo;

public class PageData<T> {
    T items;
    long total;

    public PageData() {
    }

    public PageData(T items, long total) {
        this.items = items;
        this.total = total;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
