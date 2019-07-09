package com.cskaoyan.mall_wx.service.mtan.impl;

import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.bean.CouponUserExample;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.pagedata.CouponPageData;
import com.cskaoyan.mall_wx.service.mtan.WxCouponService;
import com.cskaoyan.mapper.CartMapper;
import com.cskaoyan.mapper.CouponMapper;
import com.cskaoyan.mapper.CouponUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
