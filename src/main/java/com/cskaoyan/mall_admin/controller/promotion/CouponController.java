package com.cskaoyan.mall_admin.controller.promotion;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.bean.coupon.CouponConstant;
import com.cskaoyan.bean.coupon.CouponUserConstant;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.promotion.CouponService;
import com.cskaoyan.mall_wx.service.mtan.WxCouponService;
import com.cskaoyan.mall_wx.util.CouponCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/4
 */

@Controller
@RequestMapping("admin")
public class CouponController {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponCheck couponCheck;


    @RequestMapping("coupon/list")
    @ResponseBody
    public ResponseVO list(int page, int limit, String sort, String order, String name, Short type, Short status) {
        ResponseVO vo = new ResponseVO();
        couponCheck.checkCouponExpired();
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
        if (coupon.getType().equals(CouponConstant.TYPE_CODE)) {
            String code = couponService.generateCode();
            coupon.setCode(code);
        }
        vo = couponService.insertCoupon(coupon);
        return vo;
    }

    @RequestMapping("coupon/read")
    @ResponseBody
    public ResponseVO read(int id){
        ResponseVO vo = new ResponseVO();
        vo.setErrmsg("成功");
        vo.setData(couponService.searchCouponById(id));
        return vo;
    }

    @RequestMapping("coupon/listuser")
    @ResponseBody
    public ResponseVO list(int page, int limit, String sort, String order, Integer userId, Integer couponId, Short status) {
        ResponseVO vo = new ResponseVO();
        PageData data = couponService.getCouponUserList(page, limit, sort, order, userId, couponId, status);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }
}
