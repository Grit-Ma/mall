package com.cskaoyan.mall_wx.service.mtan;

import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.bean.wx.pagedata.CouponPageData;

import java.math.BigDecimal;
import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/9
 */
public interface WxCouponService {

    CouponPageData couponList(int page, int size, Short status, Integer userId);

//    List<Coupon> selectList(int cartId, int grouponRulesId);

    List<CouponUser> queryExpired();

    List<CouponUser> queryList(Integer userId, Integer couponId, Short status, Integer page, Integer size, String sort, String order);

    CouponUser queryOne(Integer userId, Integer couponId);

    int update(CouponUser couponUser);

    List<CouponUser> queryAll(Integer userId);

    List<Cart> queryByUidAndChecked(Integer userId);

    Cart findById(Integer id);

    Integer countCoupon(Integer couponId);

    Integer countUserAndCoupon(Integer userId, Integer couponId);

    void add(CouponUser couponUser);

    //传入couponId,获取couponPrice By cart
    BigDecimal couponPriceBycouponId(int couponId);
}
