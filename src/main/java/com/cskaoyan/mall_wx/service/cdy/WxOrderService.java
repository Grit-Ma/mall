package com.cskaoyan.mall_wx.service.cdy;

import com.cskaoyan.bean.Order;

import java.util.Map;

public interface WxOrderService {
    Map<String, Integer> orderInfo(Integer userId);
}
