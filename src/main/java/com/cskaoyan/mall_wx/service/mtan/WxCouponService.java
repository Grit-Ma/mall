package com.cskaoyan.mall_wx.service.mtan;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.wx.pagedata.CouponPageData;

import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/9
 */
public interface WxCouponService {

    CouponPageData couponList(int page, int size, Short status, Integer userId);

    List<Coupon> selectList(int cartId, int grouponRulesId);
}
