package com.cskaoyan.mall_wx.controller.xfor;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.xfor.HomeList;
import com.cskaoyan.mall_wx.service.xfor.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("wx/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    @GetMapping("index")
    @ResponseBody
    public ResponseVO home() {
        HomeList homeList = homeService.getHomeList();
        ResponseVO<HomeList> homeListResponseVO = new ResponseVO<>(0, homeList, "成功");
        return homeListResponseVO;
    }
}
