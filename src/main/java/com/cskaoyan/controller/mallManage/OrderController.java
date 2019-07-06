package com.cskaoyan.controller.mallManage;

import com.cskaoyan.bean.mallmanage.order.OrderDetail;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.mallManageService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    //admin/order/list?page=1&limit=20&sort=add_time&order=desc
    //admin/order/list?page=1&limit=20&sort=add_time&order=desc&userId=1
    //admin/order/list?page=1&limit=20&sort=add_time&order=desc&userId=&orderSn=961034487153
    //admin/order/list?page=1&limit=20&orderStatusArray=103&sort=add_time&order=desc&userId=&orderSn=
    @RequestMapping("order/list")
    @ResponseBody
    public ResponseVO orderList(int page, int limit, String sort, String order,Integer userId,String orderSn,Short orderStatusArray){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        PageData data = orderService.orderList(page,limit,sort,order,userId,orderSn,orderStatusArray);
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }

    //admin/order/detail?id=1
    @RequestMapping("order/detail")
    @ResponseBody
    public ResponseVO orderDetail(int id){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        OrderDetail orderDetail = orderService.orderDetail(id);
        if(orderDetail != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(orderDetail);
            return responseVO;
        }else {
            responseVO.setErrno(401);
            responseVO.setErrmsg("参数不对");
            return responseVO;
        }
    }
}
