package com.cskaoyan.bean.wx.xfor;

import com.cskaoyan.bean.Goods;

import java.util.List;

public class FootprintList {
    private List<Goods> footprintList;
    private long totalPages;

    public List<Goods> getFootprintList() {
        return footprintList;
    }

    public void setFootprintList(List<Goods> footprintList) {
        this.footprintList = footprintList;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
