package com.cskaoyan.controller.promotion;

import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.promotion.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public ResponseVO list(int page, int limit, String sort, String order) {
        ResponseVO vo = new ResponseVO();
        PageData data = couponService.getCouponListData(page, limit, sort, order);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }
}
