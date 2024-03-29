package com.cskaoyan.mall_admin.service.promotion;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;

import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/4
 */
public interface CouponService {

    PageData getCouponListData(int page, int limit, String sort, String order, String name, Short type, Short status);

    ResponseVO updateCoupon(Coupon coupon);

    ResponseVO deleteCoupon(Coupon coupon);

    ResponseVO insertCoupon(Coupon coupon);

    Coupon searchCouponById(int id);

    PageData getCouponUserList(int page, int limit, String sort, String order, Integer userId, Integer couponId, Short status);

    List<Coupon> queryExpired();


    String getRandomNum(Integer num);

    String generateCode();

    Coupon findByCode(String code);

    List<Coupon> findAllCoupon(int page, int size);
}
