package com.cskaoyan.mall_wx.util;

import com.cskaoyan.bean.wx.order.HandleOption;

public class OrderStatusConstant {

    public static final Short STATUS_CREATE = 101;
    public static final Short STATUS_PAY = 201;
    public static final Short STATUS_SHIP = 301;
    public static final Short STATUS_CONFIRM = 401;
    public static final Short STATUS_CANCEL = 102;
    public static final Short STATUS_AUTO_CANCEL = 103;
    public static final Short STATUS_REFUND = 202;
    public static final Short STATUS_REFUND_CONFIRM = 203;
    public static final Short STATUS_AUTO_CONFIRM = 402;

    public static String getStatusText(short orderStatus) {
        switch (orderStatus) {
            case 101:
                return "未付款";
            case 201:
                return "已付款,未发货";
            case 301:
                return "已发货,待收货";
            case 303:
                return "已退款";
            case 401:
                return "已收货";
            case 102:
                return "已取消（用户）";
            case 103:
                return "支付超时，已取消（系统）";
            case 202:
                return "订单取消，退款中";
            case 402:
                return "已收货(系统)";
            case 203:
                return "已退款";
            default:
                return "未知状态";
        }
    }

    //获取订单中每件商品的可操作信息
    public static HandleOption build(int status) {
        HandleOption handleOption = new HandleOption();

        if (status == STATUS_CREATE) {
            // 如果订单没有被取消，且没有支付，则可支付，可取消
            handleOption.setCancel(true);
            handleOption.setPay(true);
        } else if (status == STATUS_CANCEL || status == STATUS_AUTO_CANCEL) {
            // 如果订单已经取消或是已完成，则可删除
            handleOption.setDelete(true);
        } else if (status == STATUS_PAY) {
            // 如果订单已付款，没有发货，则可退款
            handleOption.setRefund(true);
        } else if (status == STATUS_REFUND) {
            // 如果订单申请退款中，没有相关操作
        } else if (status == STATUS_REFUND_CONFIRM) {
            // 如果订单已经退款，则可删除
            handleOption.setDelete(true);
        } else if (status == STATUS_SHIP) {
            // 如果订单已经发货，没有收货，则可收货操作,
            // 此时不能取消订单
            handleOption.setConfirm(true);
        } else if (status == STATUS_CONFIRM || status == STATUS_AUTO_CONFIRM) {
            // 如果订单已经支付，且已经收货，则可删除、去评论和再次购买
            handleOption.setDelete(true);
            handleOption.setComment(true);
            handleOption.setRebuy(true);
        } else {
            throw new IllegalStateException("status不支持");
        }
        return handleOption;
    }


}
