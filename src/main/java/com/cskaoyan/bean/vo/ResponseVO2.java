package com.cskaoyan.bean.vo;

public class ResponseVO2<T> {
    private long total;
    private T items;

    public ResponseVO2() {
    }

    public ResponseVO2(long total, T items) {
        this.total = total;
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }
}
