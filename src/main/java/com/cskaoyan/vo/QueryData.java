package com.cskaoyan.vo;

import java.util.List;

public class QueryData<T> {
    int total;
    List<T> items;

    public QueryData() {
    }

    public QueryData(int total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "QueryData{" +
                "total=" + total +
                ", items=" + items +
                '}';
    }
}
