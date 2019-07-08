package com.cskaoyan.bean.wx.xfor;

import com.cskaoyan.bean.Category;

import java.util.List;

public class CurrentCatalogList {
    private Category currentCategory;
    private List<Category> currentSubCategory;

    public CurrentCatalogList() {
    }

    public CurrentCatalogList(Category currentCategory, List<Category> currentSubCategory) {
        this.currentCategory = currentCategory;
        this.currentSubCategory = currentSubCategory;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public List<Category> getCurrentSubCategory() {
        return currentSubCategory;
    }

    public void setCurrentSubCategory(List<Category> currentSubCategory) {
        this.currentSubCategory = currentSubCategory;
    }
}
