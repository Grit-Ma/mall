package com.cskaoyan.bean.mallmanage.order;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderGoods;
import com.cskaoyan.bean.User;

import java.util.List;

public class OrderDetail {
    private Order order;
    private List<OrderGoods> orderGoods;
    private User user;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderGoods> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<OrderGoods> orderGoods) {
        this.orderGoods = orderGoods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
