package com.cskaoyan.mall_wx.service.cf;

import com.cskaoyan.bean.wx.order.SubmitInfo;
import com.cskaoyan.bean.wx.order.SubmitResponse;
import com.cskaoyan.bean.wx.pagedata.OrderListPageData;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface WxOrderService {

    @Transactional
    HashMap submitOrder(HttpServletRequest request, SubmitInfo submitInfo) ;

    HashMap showOrderList(int showTpe, int page, int size, String sort, String order);
}
