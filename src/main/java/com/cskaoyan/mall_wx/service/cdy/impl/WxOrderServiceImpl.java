package com.cskaoyan.mall_wx.service.cdy.impl;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderExample;
import com.cskaoyan.mall_wx.service.cdy.WxOrderService;
import com.cskaoyan.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxOrderServiceImpl implements WxOrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public Map<String, Integer> orderInfo(Integer userId) {
        OrderExample orderExample = new OrderExample();
        orderExample.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        int unpaid = 0;
        int unship = 0;
        int unrecv = 0;
        int uncomment = 0;
        if (null == orders) {
            return null;
        } else {
            Map<String, Integer> map = new HashMap<>();
            for (Order order: orders) {
                if (order.getOrderStatus() == 101) {
                    unpaid++;
                } else if (order.getOrderStatus() == 201) {
                    unship++;
                } else if (order.getOrderStatus() == 301) {
                    unrecv++;
                } else if (order.getOrderStatus() == 401) {
                    uncomment++;
                }
            }
            map.put("unpaid", unpaid);
            map.put("unship", unship);
            map.put("unrecv", unrecv);
            map.put("uncomment", uncomment);

            return map;
        }
    }

}
