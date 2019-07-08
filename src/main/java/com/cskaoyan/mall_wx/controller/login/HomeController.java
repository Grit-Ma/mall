package com.cskaoyan.mall_wx.controller.login;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.xfor.HomeList;
import com.cskaoyan.mall_wx.service.login.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;

    @RequestMapping("home/index")
    @ResponseBody
    public ResponseVO home() {
        HomeList homeList = homeService.getHomeList();
        ResponseVO<HomeList> homeListResponseVO = new ResponseVO<>(200, homeList, "ok");
        return homeListResponseVO;
    }
}
