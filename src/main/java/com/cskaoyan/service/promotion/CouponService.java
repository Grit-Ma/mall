package com.cskaoyan.service.promotion;

import com.cskaoyan.bean.vo.PageData;

/**
 * created by ZengWei
 * on 2019/7/4
 */
public interface CouponService {

    PageData getCouponListData(int page, int limit, String sort, String order);
}
