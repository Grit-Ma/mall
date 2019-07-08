package com.cskaoyan.mall_admin.controller.promotion;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.promotion.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * created by ZengWei
 * on 2019/7/4
 */

@Controller
public class CouponController {

    @Autowired
    CouponService couponService;

    @RequestMapping("coupon/list")
    @ResponseBody
    public ResponseVO list(int page, int limit, String sort, String order, String name, Short type, Short status) {
        ResponseVO vo = new ResponseVO();
        PageData data = couponService.getCouponListData(page, limit, sort, order, name, type, status);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("coupon/update")
    @ResponseBody
    public ResponseVO update(@RequestBody Coupon coupon) {
        ResponseVO vo = new ResponseVO();
        vo = couponService.updateCoupon(coupon);
        return vo;
    }

    @RequestMapping("coupon/delete")
    @ResponseBody
    public ResponseVO delete(@RequestBody Coupon coupon) {
        ResponseVO vo = new ResponseVO();
        vo = couponService.deleteCoupon(coupon);
        return vo;
    }

    @RequestMapping("coupon/create")
    @ResponseBody
    public ResponseVO insert(@RequestBody Coupon coupon) {
        ResponseVO vo = new ResponseVO();
        System.out.println(coupon);
        vo = couponService.insertCoupon(coupon);
        return vo;
    }
}
