package com.cskaoyan.mall_wx.controller.login;

import com.cskaoyan.bean.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CatalogController {
    @RequestMapping("catalog/index")
    @ResponseBody
    public ResponseVO catalogIndex() {
        return null;
    }
}
