package com.cskaoyan.mall_wx.service.mtan.impl;

import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.bean.CouponUserExample;
import com.cskaoyan.bean.coupon.CouponUserConstant;

import com.cskaoyan.bean.wx.pagedata.CouponPageData;
import com.cskaoyan.mall_wx.service.mtan.WxCouponService;
import com.cskaoyan.mapper.CartMapper;
import com.cskaoyan.mapper.CouponMapper;
import com.cskaoyan.mapper.CouponUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/9
 */

@Service
public class WxCouponServiceImpl implements WxCouponService {

    @Autowired
    CouponUserMapper couponUserMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public CouponPageData couponList(int page, int size, Short status, Integer userId){
        PageHelper.startPage(page,size);
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andStatusEqualTo(status);
        criteria.andUserIdEqualTo(userId);
        List<CouponUser> list1 = couponUserMapper.selectByExample(couponUserExample);
        List<Coupon> list2 = new ArrayList<>();
        for (CouponUser couponUser : list1) {
            Coupon coupon = couponMapper.selectByPrimaryKey(couponUser.getCouponId());
            list2.add(coupon);
        }
        PageInfo pageInfo = new PageInfo(list2);
        return new CouponPageData(pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public List<Coupon> selectList(int cartId, int grouponRulesId) {
        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        Integer userId = cart.getUserId();
        Short status = 0;
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andStatusEqualTo(status);
        criteria.andUserIdEqualTo(userId);
        List<CouponUser> list1 = couponUserMapper.selectByExample(couponUserExample);
        List<Coupon> list2 = new ArrayList<>();
        for (CouponUser couponUser : list1) {
            Coupon coupon = couponMapper.selectByPrimaryKey(couponUser.getCouponId());
            if(cart.getPrice().compareTo(coupon.getMin()) > 0)
            list2.add(coupon);
        }
        return list2;
    }

    @Override
    public List<CouponUser> queryExpired() {
        CouponUserExample example = new CouponUserExample();
        example.or().andStatusEqualTo(CouponUserConstant.STATUS_USABLE).andEndTimeLessThan(new Date()).andDeletedEqualTo(false);
        return couponUserMapper.selectByExample(example);
    }

    @Override
    public List<CouponUser> queryList(Integer userId, Integer couponId, Short status, Integer page, Integer size, String sort, String order) {
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(couponId != null){
            criteria.andCouponIdEqualTo(couponId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(page) && !StringUtils.isEmpty(size)) {
            PageHelper.startPage(page, size);
        }

        return couponUserMapper.selectByExample(example);
    }
    
    @Override
    public CouponUser queryOne(Integer userId, Integer couponId) {
        List<CouponUser> couponUserList = queryList(userId, couponId, CouponUserConstant.STATUS_USABLE, 1, 1, "add_time", "desc");
        if(couponUserList.size() == 0){
            return null;
        }
        return couponUserList.get(0);
    }

    @Override
    public int update(CouponUser couponUser) {
        couponUser.setUpdateTime(new Date());
        return couponUserMapper.updateByPrimaryKeySelective(couponUser);
    }
}
