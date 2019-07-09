package com.cskaoyan.bean.vo;

import com.cskaoyan.bean.*;

import java.util.List;

public class GoodsDeatil {
    private List<GoodsAttribute> attributes;
    private Brand brand;
    private CommentVo comment;
    private List groupon;
    private Goods info;
    private List<Issue> issue;
    private List<GoodsProduct> productList;
    private String shareImage;
    private List<GoodsSpecification> specificationList;
    private Integer userHasCollect;


    public List<GoodsAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<GoodsAttribute> attributes) {
        this.attributes = attributes;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public CommentVo getComment() {
        return comment;
    }

    public void setComment(CommentVo comment) {
        this.comment = comment;
    }

    public List getGroupon() {
        return groupon;
    }

    public void setGroupon(List groupon) {
        this.groupon = groupon;
    }

    public Goods getInfo() {
        return info;
    }

    public void setInfo(Goods info) {
        this.info = info;
    }

    public List<Issue> getIssue() {
        return issue;
    }

    public void setIssue(List<Issue> issue) {
        this.issue = issue;
    }

    public List<GoodsProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<GoodsProduct> productList) {
        this.productList = productList;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public List<GoodsSpecification> getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(List<GoodsSpecification> specificationList) {
        this.specificationList = specificationList;
    }

    public Integer getUserHasCollect() {
        return userHasCollect;
    }

    public void setUserHasCollect(Integer userHasCollect) {
        this.userHasCollect = userHasCollect;
    }
}
