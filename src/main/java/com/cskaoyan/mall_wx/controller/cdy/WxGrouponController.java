package com.cskaoyan.mall_wx.controller.cdy;

import com.cskaoyan.bean.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wx/groupon")
public class WxGrouponController {

    @GetMapping("list")
    public ResponseVO list() {

        return null;
    }

    @GetMapping("my")
    public ResponseVO my() {
        return null;
    }

}
