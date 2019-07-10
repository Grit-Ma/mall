package com.cskaoyan.mall_wx.controller.mtan;

import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.bean.GrouponRules;
import com.cskaoyan.bean.coupon.CouponConstant;
import com.cskaoyan.bean.coupon.CouponVo;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.pagedata.CouponPageData;
import com.cskaoyan.mall_admin.service.promotion.CouponService;
import com.cskaoyan.mall_admin.service.promotion.GroupService;
import com.cskaoyan.mall_wx.service.CartService;
import com.cskaoyan.mall_wx.service.mtan.CouponVerifyService;
import com.cskaoyan.mall_wx.service.mtan.WxCouponService;
import com.cskaoyan.mall_wx.util.UserTokenManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * created by ZengWei
 * on 2019/7/9
 */

@Controller
@RequestMapping("wx")
public class WxCouponController {

    @Autowired
    WxCouponService wxCouponService;

    @Autowired
    GroupService groupService;

    @Autowired
    CartService cartService;

    @Autowired
    CouponVerifyService couponVerifyService;

    @Autowired
    CouponService couponService;


    @RequestMapping("coupon/mylist")
    @ResponseBody
    public ResponseVO couponList(int page, int size, Short status, HttpServletRequest request){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        CouponPageData data = wxCouponService.couponList(page, size, status, userId);
        if(data != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(data);
            return responseVO;
        }else {
            responseVO.setErrmsg("failure");
            return responseVO;
        }
    }

    @RequestMapping("coupon/list")
    @ResponseBody
    public ResponseVO list(int page, int size){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(couponService.findAllCoupon(page, size));
        return  responseVO;
    }

    @RequestMapping("coupon/selectlist")
    @ResponseBody
    public ResponseVO selectList(Integer userId, Integer cartId, Integer grouponRulesId, HttpServletRequest request) {
        ResponseVO responseVO = new ResponseVO();
        String tokenKey = request.getHeader("X-Litemall-Token");
        userId = UserTokenManager.getUserId(tokenKey);
        // 团购优惠
        BigDecimal grouponPrice = new BigDecimal(0.00);
        GrouponRules grouponRules = groupService.searchRulesById(grouponRulesId);
        if (grouponRules != null) {
            grouponPrice = grouponRules.getDiscount();
        }

        // 商品价格
        List<Cart> checkedGoodsList = null;
        if (cartId == null || cartId.equals(0)) {
            checkedGoodsList = wxCouponService.queryByUidAndChecked(userId);
        } else {
            Cart cart = wxCouponService.findById(cartId);
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }
        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        for (Cart cart : checkedGoodsList) {
            //  只有当团购规格商品ID符合才进行团购优惠
            if (grouponRules != null && grouponRules.getGoodsId().equals(cart.getGoodsId())) {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().subtract(grouponPrice).multiply(new BigDecimal(cart.getNumber())));
            } else {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }

        // 计算优惠券可用情况
        List<CouponUser> couponUserList = wxCouponService.queryAll(userId);
        List<Coupon> availableCouponList = new ArrayList<>(couponUserList.size());
        for (CouponUser couponUser : couponUserList) {
            Coupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), checkedGoodsPrice);
            if (coupon == null) {
                continue;
            }
            availableCouponList.add(coupon);
        }
        responseVO.setData(availableCouponList);
        return  responseVO;
    }

    @RequestMapping("coupon/receive")
    @ResponseBody
    public ResponseVO receive(@RequestBody Map<String, Object>data , HttpServletRequest request) {
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        Integer couponId = (Integer) data.get("couponId");
        Coupon coupon = couponService.searchCouponById(couponId);
        ResponseVO responseVO = new ResponseVO();

        // 当前已领取数量和总数量比较
        Integer total = coupon.getTotal();
        Integer totalCoupons = wxCouponService.countCoupon(couponId);
        if((total != 0) && (totalCoupons >= total)){
            responseVO.setErrmsg("优惠券已领完");
            responseVO.setErrno(741);
            return responseVO;
        }

        // 当前用户已领取数量和用户限领数量比较
        Integer limit = coupon.getLimit().intValue();
        Integer userCounpons = wxCouponService.countUserAndCoupon(userId, couponId);
        if((limit != 0) && (userCounpons >= limit)){
            responseVO.setErrmsg("优惠券已经领取过");
            responseVO.setErrno(741);
            return responseVO;
        }

        // 优惠券分发类型
        // 例如注册赠券类型的优惠券不能领取
        Short type = coupon.getType();
        if(type.equals(CouponConstant.TYPE_REGISTER)){
            responseVO.setErrmsg("新用户优惠券自动发送");
            responseVO.setErrno(741);
            return responseVO;
        }
        else if(type.equals(CouponConstant.TYPE_CODE)){
            responseVO.setErrmsg("优惠券只能兑换");
            responseVO.setErrno(741);
            return responseVO;
        }
        else if(!type.equals(CouponConstant.TYPE_COMMON)){
            responseVO.setErrmsg("优惠券类型不支持");
            responseVO.setErrno(741);
            return responseVO;
        }

        // 优惠券状态，已下架或者过期不能领取
        Short status = coupon.getStatus();
        if(status.equals(CouponConstant.STATUS_OUT)){
            responseVO.setErrmsg("优惠券已领完");
            responseVO.setErrno(741);
            return responseVO;
        }
        else if(status.equals(CouponConstant.STATUS_EXPIRED)){
            responseVO.setErrmsg("优惠券已经过期");
            responseVO.setErrno(741);
            return responseVO;
        }

        // 用户领券记录
        CouponUser couponUser = new CouponUser();
        couponUser.setCouponId(couponId);
        couponUser.setUserId(userId);
        Short timeType = coupon.getTimeType();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            couponUser.setStartTime(coupon.getStartTime());
            couponUser.setEndTime(coupon.getEndTime());
        }
        else{
            Date date = new Date();
            couponUser.setStartTime(date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,coupon.getDays());
            date=calendar.getTime();
            couponUser.setEndTime(date);
        }
        wxCouponService.add(couponUser);
        responseVO.setErrmsg("领取成功");
        return responseVO;
    }


    @RequestMapping("coupon/exchange")
    @ResponseBody
    public ResponseVO exchange(@RequestBody Map<String, Object>data , HttpServletRequest request) {
        ResponseVO responseVO = new ResponseVO();
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        String  code = (String) data.get("code");
        Coupon coupon = couponService.findByCode(code);
        if(coupon == null){
            responseVO.setErrno(742);
            responseVO.setErrmsg("兑换码不正确");
            return responseVO;
        }
        Integer couponId = coupon.getId();
        // 当前已领取数量和总数量比较
        Integer total = coupon.getTotal();
        Integer totalCoupons = wxCouponService.countCoupon(couponId);
        if((total != 0) && (totalCoupons >= total)){
            responseVO.setErrmsg("优惠券已兑换");
            responseVO.setErrno(740);
            return responseVO;
        }

        // 当前用户已领取数量和用户限领数量比较
        Integer limit = coupon.getLimit().intValue();
        Integer userCounpons = wxCouponService.countUserAndCoupon(userId, couponId);
        if((limit != 0) && (userCounpons >= limit)){
            responseVO.setErrno(740);
            responseVO.setErrmsg("优惠券已兑换");
            return responseVO;
        }

        // 优惠券分发类型
        // 例如注册赠券类型的优惠券不能领取
        Short type = coupon.getType();
        if(type.equals(CouponConstant.TYPE_REGISTER)){
            responseVO.setErrmsg("新用户优惠券自动发送");
            responseVO.setErrno(741);
            return responseVO;
        }
        else if(type.equals(CouponConstant.TYPE_COMMON)){
            responseVO.setErrmsg("优惠券只能领取");
            responseVO.setErrno(741);
            return responseVO;
        }
        else if(!type.equals(CouponConstant.TYPE_CODE)){
            responseVO.setErrmsg("优惠券类型不支持");
            responseVO.setErrno(741);
            return responseVO;
        }

        // 优惠券状态，已下架或者过期不能领取
        Short status = coupon.getStatus();
        if(status.equals(CouponConstant.STATUS_OUT)){
            responseVO.setErrno(740);
            responseVO.setErrmsg("优惠券已兑换");
            return responseVO;
        }
        else if(status.equals(CouponConstant.STATUS_EXPIRED)){
            responseVO.setErrmsg("优惠券已经过期");
            responseVO.setErrno(741);
            return responseVO;
        }

        // 用户领券记录
        CouponUser couponUser = new CouponUser();
        couponUser.setCouponId(couponId);
        couponUser.setUserId(userId);
        Short timeType = coupon.getTimeType();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            couponUser.setStartTime(coupon.getStartTime());
            couponUser.setEndTime(coupon.getEndTime());
        }
        else{
            Date date = new Date();
            couponUser.setStartTime(date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,coupon.getDays());
            date=calendar.getTime();
            couponUser.setEndTime(date);
        }
        wxCouponService.add(couponUser);
        responseVO.setErrmsg("兑换成功");
        return responseVO;
    }
}
