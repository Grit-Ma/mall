package com.cskaoyan.bean.wx.order;

public class SubmitResponse {
    private Integer orderId;

    public SubmitResponse() {
    }

    public SubmitResponse(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
