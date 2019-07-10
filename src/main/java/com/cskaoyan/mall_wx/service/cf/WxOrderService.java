package com.cskaoyan.mall_wx.service.cf;

import com.cskaoyan.bean.wx.order.SubmitInfo;
import com.cskaoyan.bean.wx.order.SubmitResponse;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public interface WxOrderService {

    @Transactional
    HashMap submitOrder(HttpServletRequest request, SubmitInfo submitInfo) ;

    HashMap showOrderList(int showTpe, int page, int size, String sort, String order);

    //获取订单详情
    HashMap detail(HttpServletRequest request, Integer orderId);

    @Transactional
    HashMap cancelOrder(HttpServletRequest request, SubmitResponse submitResponse);

    Map<String, Integer> orderInfo(Integer userId);
}
