package com.cskaoyan.bean.wx;

import java.util.List;

public class WxListVo<T> {

    private Integer count;

    private List<T> data;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
