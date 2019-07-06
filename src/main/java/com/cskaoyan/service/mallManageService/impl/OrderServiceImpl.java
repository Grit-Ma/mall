package com.cskaoyan.service.mallManageService.impl;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderExample;
import com.cskaoyan.bean.mallmanage.order.OrderDetail;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.OrderMapper;
import com.cskaoyan.service.mallManageService.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Override
    public PageData orderList(int page, int limit, String sort, String order,Integer userId,String orderSn,Short orderStatusArray) {
        PageHelper.startPage(page,limit);
        OrderExample example = new OrderExample();
        if(userId != null){
            example.createCriteria().andUserIdEqualTo(userId);
        }
        if(orderSn != null && orderSn.trim().length() !=0){
            example.createCriteria().andOrderSnEqualTo(orderSn.trim());
        }
        if(orderStatusArray != null){
            example.createCriteria().andOrderStatusEqualTo(orderStatusArray);
        }
        example.setOrderByClause(sort + "  " + order);
        example.createCriteria().andDeletedEqualTo(false);
        List<Order> list = orderMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return new PageData(pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public OrderDetail orderDetail(int id) {
        OrderDetail result = orderMapper.selectOrderDetailById(id);
        return result;
    }
}
