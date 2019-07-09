package com.cskaoyan.bean.vo;

import com.cskaoyan.bean.Category;
import java.util.List;

public class GoodsListVo {
    private int count;
    private List<Category> filterCategoryList;
    private List<WxGoodsVo> goodsList;

    public GoodsListVo() {
    }



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Category> getFilterCategoryList() {
        return filterCategoryList;
    }

    public void setFilterCategoryList(List<Category> filterCategoryList) {
        this.filterCategoryList = filterCategoryList;
    }

    public List<WxGoodsVo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<WxGoodsVo> goodsList) {
        this.goodsList = goodsList;
    }


}
