package com.cskaoyan.bean.vo;

import com.cskaoyan.bean.Category;

import java.util.List;

public class CategoryVo {
    private List<Category> brotherCategory;
    private Category currentCategory;
    private Category parentCategory;

    public List<Category> getBrotherCategory() {
        return brotherCategory;
    }

    public CategoryVo(List<Category> brotherCategory, Category currentCategory, Category parentCategory) {
        this.brotherCategory = brotherCategory;
        this.currentCategory = currentCategory;
        this.parentCategory = parentCategory;
    }

    public void setBrotherCategory(List<Category> brotherCategory) {
        this.brotherCategory = brotherCategory;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
