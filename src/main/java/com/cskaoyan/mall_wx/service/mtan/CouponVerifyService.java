package com.cskaoyan.mall_wx.service.mtan;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.bean.coupon.CouponConstant;
import com.cskaoyan.mall_admin.service.promotion.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * created by ZengWei
 * on 2019/7/9
 */

@Service
public class CouponVerifyService {
    @Autowired WxCouponService wxCouponService;

    @Autowired CouponService couponService;

    public Coupon checkCoupon(Integer userId, Integer couponId, BigDecimal checkedGoodsPrice) {
        Coupon coupon = couponService.searchCouponById(couponId);
        CouponUser couponUser = wxCouponService.queryOne(userId, couponId);
        if (coupon == null || couponUser == null) {
            return null;
        }

        // 检查是否超期
        Short timeType = coupon.getTimeType();
        Short days = coupon.getDays();
        Date date = new Date();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            if (date.compareTo(coupon.getStartTime()) < 0 || date.compareTo(coupon.getEndTime()) > 0) {
                return null;
            }
        }
        else if(timeType.equals(CouponConstant.TIME_TYPE_DAYS)) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(coupon.getAddTime());
            calendar.add(Calendar.DATE,days);//把日期往后增加
            Date date1 = calendar.getTime();
            if (date.compareTo(date1) > 0) {
                return null;
            }
        }
        else {
            return null;
        }

        // 检测商品是否符合
        Short goodType = coupon.getGoodsType();
        if (!goodType.equals(CouponConstant.GOODS_TYPE_ALL)) {
            return null;
        }

        // 检测订单状态
        Short status = coupon.getStatus();
        if (!status.equals(CouponConstant.STATUS_NORMAL)) {
            return null;
        }
        // 检测是否满足最低消费
        if (checkedGoodsPrice.compareTo(coupon.getMin()) == -1) {
            return null;
        }

        return coupon;
    }
}
