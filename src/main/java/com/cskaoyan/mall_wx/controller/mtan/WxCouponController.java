package com.cskaoyan.mall_wx.controller.mtan;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.pagedata.CouponPageData;
import com.cskaoyan.mall_admin.service.promotion.CouponService;
import com.cskaoyan.mall_wx.service.mtan.WxCouponService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * created by ZengWei
 * on 2019/7/9
 */

@Controller
@RequestMapping("wx")
public class WxCouponController {

    @Autowired
    WxCouponService wxCouponService;


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

    @RequestMapping("coupon/selectlist")
    @ResponseBody
    public ResponseVO selectList(int cartId, int grouponRulesId) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
git        responseVO.setData(wxCouponService.selectList(cartId, grouponRulesId));
        return  responseVO;
    }
}
