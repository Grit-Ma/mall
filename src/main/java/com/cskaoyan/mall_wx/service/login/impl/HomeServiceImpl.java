package com.cskaoyan.mall_wx.service.login.impl;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.wx.xfor.FloorGoodsList;
import com.cskaoyan.bean.wx.xfor.GrouponList;
import com.cskaoyan.bean.wx.xfor.HomeList;
import com.cskaoyan.mall_wx.service.login.HomeService;
import com.cskaoyan.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    AdMapper adMapper;

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public HomeList getHomeList() {
        HomeList homeList = new HomeList();
        homeList.setBannerList(getBannerList());
        homeList.setBrandList(getBrandList());
        homeList.setChannel(getChannel());
        homeList.setCouponList(getCouponList());
        homeList.setFloorGoodsList(getFloorGoodsList());
        homeList.setGrouponList(getGrouponList());
        homeList.setHotGoodsList(getHotGoodsList());
        homeList.setNewGoodsList(getNewGoodsList());
        homeList.setTopicList(getTopicList());
        return homeList;
    }

    private List<Goods> getTopicList() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andBrandIdBetween(1010000, 1011004);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return goodsList;
    }

    private List<Goods> getNewGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andBrandIdBetween(1009009, 1009027);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return goodsList;
    }

    private List<Goods> getHotGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andBrandIdBetween(1006002, 1006051);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return goodsList;
    }

    private List<GrouponList> getGrouponList() {
        return null;
    }

    private List<FloorGoodsList> getFloorGoodsList() {
        ArrayList<FloorGoodsList> floorGoodsLists = new ArrayList<>();
        for (int i = 1005007; i <= 1005010; i++) {
            GoodsExample goodsExample = new GoodsExample();
            goodsExample.createCriteria().andCategoryIdEqualTo(i);
            List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
            FloorGoodsList floorGoodsList = new FloorGoodsList();
            floorGoodsList.setId(i);
            floorGoodsList.setName(categoryMapper.selectByPrimaryKey(i).getName());
            floorGoodsList.setGoodsList(goodsList);
            floorGoodsLists.add(floorGoodsList);
        }
        return floorGoodsLists;
    }

    private List<Coupon> getCouponList() {
        List<Coupon> couponList = couponMapper.selectByExample(new CouponExample());
        return couponList;
    }

    private List<Category> getChannel() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andIdLessThanOrEqualTo(1005010);
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        return categoryList;
    }

    private List<Brand> getBrandList() {
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andIdLessThanOrEqualTo(1001010);
        List<Brand> brandList = brandMapper.selectByExample(brandExample);
        return brandList;
    }

    private List<Ad> getBannerList() {
        List<Ad> adList = adMapper.selectByExample(new AdExample());
        return adList;
    }
}
