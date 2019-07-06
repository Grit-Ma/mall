package com.cskaoyan.service.mallManageService;

import com.cskaoyan.bean.mallmanage.order.OrderDetail;
import com.cskaoyan.bean.vo.PageData;

public interface OrderService {
    PageData orderList(int page, int limit, String sort, String order, Integer userId, String orderSn, Short orderStatusArray);

    OrderDetail orderDetail(int id);
}
