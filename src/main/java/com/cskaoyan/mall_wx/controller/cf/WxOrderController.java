package com.cskaoyan.mall_wx.controller.cf;

import com.cskaoyan.bean.wx.order.SubmitInfo;
import com.cskaoyan.mall_wx.service.cf.WxOrderService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
                             @RequestParam(defaultValue = "desc") String order){
        return wxOrderService.showOrderList(showType, page, size,sort,order);
    }

    @GetMapping("order/detail")
    public HashMap orderDetail(HttpServletRequest request,@RequestParam int orderId){
        return wxOrderService.detail(request,orderId);
    }

    @PostMapping("order/submit")
    public HashMap orderSubmit(@RequestBody SubmitInfo submitInfo, HttpServletRequest request){
        return wxOrderService.submitOrder(request,submitInfo);
    }

//    @PostMapping("order/prepay")
//    public HashMap orderPrePay(){
//
//    }

//    @PostMapping("order/cancel")
//    public HashMap orderCancel(){
//
//    }


}
