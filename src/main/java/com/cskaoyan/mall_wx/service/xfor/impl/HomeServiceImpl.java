package com.cskaoyan.mall_wx.service.xfor.impl;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.wx.xfor.FloorGoodsList;
import com.cskaoyan.bean.wx.xfor.GrouponList;
import com.cskaoyan.bean.wx.xfor.HomeList;
import com.cskaoyan.mall_wx.service.xfor.HomeService;
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

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    TopicMapper topicMapper;

    @Override
    public HomeList getHomeList() {
        HomeList homeList = new HomeList();
        homeList.setBanner(getBannerList());
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

    private List<Topic> getTopicList() {
        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andIdBetween(264, 268);
        List<Topic> topicList = topicMapper.selectByExample(topicExample);
        return topicList;
    }

    private List<Goods> getNewGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIsNewEqualTo(true).andIsHotEqualTo(false);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return goodsList;
    }

    private List<Goods> getHotGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIsHotEqualTo(true).andIsNewEqualTo(false);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return goodsList;
    }

    private List<GrouponList> getGrouponList() {
        ArrayList<GrouponList> grouponLists = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(i);
            GrouponList grouponList = new GrouponList();
            grouponList.setGoods(goodsMapper.selectByPrimaryKey(grouponRules.getGoodsId()));
            grouponList.setGroupon_member(grouponRules.getDiscountMember());
            grouponList.setGroupon_price(grouponRules.getDiscount());
            grouponLists.add(grouponList);
        }
        return grouponLists;
    }

    private List<FloorGoodsList> getFloorGoodsList() {
        ArrayList<FloorGoodsList> floorGoodsLists = new ArrayList<>();
        for (int i = 1005000; i <= 1005002; i++) {
            GoodsExample goodsExample = new GoodsExample();
            goodsExample.createCriteria().andCategoryIdEqualTo(getCategoryId(i));
            List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
            FloorGoodsList floorGoodsList = new FloorGoodsList();
            floorGoodsList.setId(i);
            floorGoodsList.setName(categoryMapper.selectByPrimaryKey(i).getName());
            floorGoodsList.setGoodsList(goodsList);
            floorGoodsLists.add(floorGoodsList);
        }
        return floorGoodsLists;
    }

    private int getCategoryId(int i) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(i);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        return categories.get(0).getId();
    }

    private List<Coupon> getCouponList() {
        List<Coupon> couponList = couponMapper.selectByExample(new CouponExample());
        return couponList;
    }

    public List<Category> getChannel() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelEqualTo("L1");
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
