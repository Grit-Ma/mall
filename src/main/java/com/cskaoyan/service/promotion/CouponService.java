package com.cskaoyan.service.promotion;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;

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
}
