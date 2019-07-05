package com.cskaoyan.service.promotion.Impl;

import com.cskaoyan.bean.Ad;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.CouponMapper;
import com.cskaoyan.service.promotion.CouponService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/4
 */

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponMapper couponMapper;

    @Override
    public PageData getCouponListData(int page, int limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        CouponExample example = new CouponExample();
        example.setOrderByClause(sort+" "+order);
        List<Coupon> coupons = couponMapper.selectByExample(example);
        PageInfo<Coupon> pageinfo = new PageInfo(coupons);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }
}
