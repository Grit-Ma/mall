package com.cskaoyan.bean.vo;

import java.util.List;

public class CatAndBrandVo {
    private List brandList;
    private List categoryList;

    public CatAndBrandVo(List brandList, List categoryList) {
        this.brandList = brandList;
        this.categoryList = categoryList;
    }

    public List getBrandList() {
        return brandList;
    }

    public void setBrandList(List brandList) {
        this.brandList = brandList;
    }

    public List getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List categoryList) {
        this.categoryList = categoryList;
    }

    public CatAndBrandVo() {
    }
}
