package com.cskaoyan.mall_wx.controller.cf;

import com.cskaoyan.bean.wx.order.SubmitInfo;
import com.cskaoyan.bean.wx.order.SubmitResponse;
import com.cskaoyan.mall_wx.service.cf.WxOrderService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static com.cskaoyan.mall_wx.util.WxResponseCode.ORDER_PAY_FAIL;


@RestController
@RequestMapping("wx")
public class WxOrderController {

    @Autowired
    WxOrderService wxOrderService;

    @GetMapping("order/list")
    public HashMap orderList(@RequestParam(defaultValue = "0") Integer showType,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size,
                             @RequestParam(defaultValue = "add_time") String sort,
                             @RequestParam(defaultValue = "desc") String order,
                             HttpServletRequest request){
        return wxOrderService.showOrderList(showType, page, size,sort,order,request);
    }

    @GetMapping("order/detail")
    public HashMap orderDetail(HttpServletRequest request,@RequestParam int orderId){
        return wxOrderService.detail(request,orderId);
    }

    @PostMapping("order/submit")
    public HashMap orderSubmit(@RequestBody SubmitInfo submitInfo, HttpServletRequest request){
        return wxOrderService.submitOrder(request,submitInfo);
    }

    @PostMapping("order/prepay")
    public HashMap orderPrePay(HttpServletRequest request, @RequestBody SubmitResponse submitResponse){
        return wxOrderService.prePay(request, submitResponse);
       // return WrapTool.setResponseFailure(ORDER_PAY_FAIL, "订单不能支付");
    }

    @PostMapping("order/cancel")
    public HashMap orderCancel(HttpServletRequest request, @RequestBody SubmitResponse submitResponse){
        return wxOrderService.cancelOrder(request,submitResponse);
    }

    @PostMapping("order/delete")
    public HashMap orderDelete(HttpServletRequest request, @RequestBody SubmitResponse submitResponse){
        return wxOrderService.deleteOrder(request,submitResponse);
    }

    @PostMapping("order/refund")
    public HashMap orderRefund(HttpServletRequest request, @RequestBody SubmitResponse submitResponse){
        return wxOrderService.refundOrder(request,submitResponse);
    }

    @PostMapping("order/confirm")
    public HashMap orderConfirm(HttpServletRequest request, @RequestBody SubmitResponse submitResponse){
        return wxOrderService.comfirmOrder(request,submitResponse);
    }

}
