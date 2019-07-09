package com.cskaoyan.bean.wx.order;


import com.cskaoyan.bean.OrderGoods;

import java.math.BigDecimal;
import java.util.List;

public class WxOrder {

    private Integer id;

    List<OrderGoods> goodList; //11

    HandleOption handleOption;//11

    private BigDecimal actualPrice;

    private String orderSn;//

    private String orderStatusText; //11

    private Short order_status;//

    public WxOrder() {
    }

    public WxOrder(Integer id, List<OrderGoods> goodList, HandleOption handleOption, BigDecimal actualPrice, String orderSn, String orderStatusText, Short order_status) {
        this.id = id;
        this.goodList = goodList;
        this.handleOption = handleOption;
        this.actualPrice = actualPrice;
        this.orderSn = orderSn;
        this.orderStatusText = orderStatusText;
        this.order_status = order_status;
    }

    public Short getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Short order_status) {
        this.order_status = order_status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrderGoods> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<OrderGoods> goodList) {
        this.goodList = goodList;
    }

    public HandleOption getHandleOption() {
        return handleOption;
    }

    public void setHandleOption(HandleOption handleOption) {
        this.handleOption = handleOption;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderStatusText() {
        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText) {
        this.orderStatusText = orderStatusText;
    }
}
