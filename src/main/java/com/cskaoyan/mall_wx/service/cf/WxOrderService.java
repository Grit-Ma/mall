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

    HashMap showOrderList(int showTpe, int page, int size, String sort, String order, HttpServletRequest request);

    //获取订单详情
    HashMap detail(HttpServletRequest request, Integer orderId);

    @Transactional
    HashMap cancelOrder(HttpServletRequest request, SubmitResponse submitResponse);

    /*
        删除订单
     */
    HashMap deleteOrder(HttpServletRequest request, SubmitResponse submitResponse);

    Map<String, Integer> orderInfo(Integer userId);

    @Transactional
    HashMap prePay(HttpServletRequest request, SubmitResponse submitResponse);

    @Transactional
    HashMap refundOrder(HttpServletRequest request, SubmitResponse submitResponse);

    HashMap comfirmOrder(HttpServletRequest request, SubmitResponse submitResponse);
}
