package com.cskaoyan.bean.vo;

import com.cskaoyan.bean.Goods;

import java.util.List;

public class ProductVo {
    private List attributes;
    private List categoryIds;
    private Goods goods;
    private List products;
    private List specifications;

    public List getAttributes() {
        return attributes;
    }

    public void setAttributes(List attributes) {
        this.attributes = attributes;
    }

    public List getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public List getProducts() {
        return products;
    }

    public void setProducts(List products) {
        this.products = products;
    }

    public List getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List specifications) {
        this.specifications = specifications;
    }

    public ProductVo(List attributes, List categoryIds, Goods goods, List products, List specifications) {
        this.attributes = attributes;
        this.categoryIds = categoryIds;
        this.goods = goods;
        this.products = products;
        this.specifications = specifications;
    }

    public ProductVo() {
    }
}
