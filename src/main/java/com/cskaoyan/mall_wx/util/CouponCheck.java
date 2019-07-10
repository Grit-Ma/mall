package com.cskaoyan.mall_wx.util;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.bean.coupon.CouponConstant;
import com.cskaoyan.bean.coupon.CouponUserConstant;
import com.cskaoyan.mall_admin.service.promotion.CouponService;
import com.cskaoyan.mall_wx.service.mtan.WxCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/9
 */

@Component
public class CouponCheck {

    @Autowired
    CouponService couponService;

    @Autowired
    WxCouponService wxCouponService;

    public void checkCouponExpired() {
        List<Coupon> couponList = couponService.queryExpired();
        for (Coupon coupon : couponList) {
            coupon.setStatus(CouponConstant.STATUS_EXPIRED);
            couponService.updateCoupon(coupon);
        }

        List<CouponUser> couponUserList = wxCouponService.queryExpired();
        for (CouponUser couponUser : couponUserList) {
            couponUser.setStatus(CouponUserConstant.STATUS_EXPIRED);
            wxCouponService.update(couponUser);
        }

        System.out.println("更新优惠券状态...");
    }

}
