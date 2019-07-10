package com.cskaoyan.mall_wx.service.mtan.impl;

import com.cskaoyan.bean.*;
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


import java.math.BigDecimal;
import java.util.*;

import static com.cskaoyan.bean.coupon.CouponConstant.TIME_TYPE_DAYS;

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
        Date date = new Date();
        PageHelper.startPage(page,size);
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andStatusEqualTo(status);
        criteria.andUserIdEqualTo(userId);
        List<CouponUser> list1 = couponUserMapper.selectByExample(couponUserExample);
        List<Coupon> list2 = new ArrayList<>();
        for (CouponUser couponUser : list1) {
            Coupon coupon = couponMapper.selectByPrimaryKey(couponUser.getCouponId());
            if(coupon.getTimeType() == TIME_TYPE_DAYS) {
                coupon.setStartTime(coupon.getAddTime());
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(coupon.getAddTime());
                calendar.add(Calendar.DATE,coupon.getDays());
                date=calendar.getTime();
                coupon.setEndTime(date);
            }
            list2.add(coupon);
        }
        PageInfo pageInfo = new PageInfo(list2);
        return new CouponPageData(pageInfo.getList(),pageInfo.getTotal());
    }

/*    @Override
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
    }*/

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

    @Override
    public List<CouponUser> queryAll(Integer userId) {
        return queryList(userId, null, CouponUserConstant.STATUS_USABLE, null, null, "add_time", "desc");
    }

    @Override
    public List<Cart> queryByUidAndChecked(Integer userId) {
        CartExample example = new CartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    @Override
    public Cart findById(Integer id) {
        return cartMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer countCoupon(Integer couponId) {
        CouponUserExample example = new CouponUserExample();
        example.or().andCouponIdEqualTo(couponId).andDeletedEqualTo(false);
        return (int)couponUserMapper.countByExample(example);
    }

    @Override
    public Integer countUserAndCoupon(Integer userId, Integer couponId) {
        CouponUserExample example = new CouponUserExample();
        example.or().andUserIdEqualTo(userId).andCouponIdEqualTo(couponId).andDeletedEqualTo(false);
        return (int)couponUserMapper.countByExample(example);
    }

    @Override
    public void add(CouponUser couponUser) {
        couponUser.setAddTime(new Date());
        couponUser.setUpdateTime(new Date());
        couponUserMapper.insertSelective(couponUser);
    }


    //传入couponId,获取couponPrice By cart
    @Override
    public BigDecimal couponPriceBycouponId(int couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if (coupon == null){
            return new BigDecimal(0);
        }else {
            return coupon.getDiscount();
        }

    }
}
