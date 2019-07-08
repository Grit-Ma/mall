package com.cskaoyan.bean.wx.xfor;

import com.cskaoyan.bean.*;

import java.util.List;

public class HomeList {
    private List<Ad> bannerList;
    private List<Brand> brandList;
    private List<Category> channel;
    private List<Coupon> couponList;
    private List<FloorGoodsList> floorGoodsList;
    private List<GrouponList> grouponList;
    private List<Goods> hotGoodsList;
    private List<Goods> newGoodsList;
    private List<Goods> topicList;

    public List<Ad> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Ad> bannerList) {
        this.bannerList = bannerList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Category> getChannel() {
        return channel;
    }

    public void setChannel(List<Category> channel) {
        this.channel = channel;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public List<FloorGoodsList> getFloorGoodsList() {
        return floorGoodsList;
    }

    public void setFloorGoodsList(List<FloorGoodsList> floorGoodsList) {
        this.floorGoodsList = floorGoodsList;
    }

    public List<GrouponList> getGrouponList() {
        return grouponList;
    }

    public void setGrouponList(List<GrouponList> grouponList) {
        this.grouponList = grouponList;
    }

    public List<Goods> getHotGoodsList() {
        return hotGoodsList;
    }

    public void setHotGoodsList(List<Goods> hotGoodsList) {
        this.hotGoodsList = hotGoodsList;
    }

    public List<Goods> getNewGoodsList() {
        return newGoodsList;
    }

    public void setNewGoodsList(List<Goods> newGoodsList) {
        this.newGoodsList = newGoodsList;
    }

    public List<Goods> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Goods> topicList) {
        this.topicList = topicList;
    }
}
