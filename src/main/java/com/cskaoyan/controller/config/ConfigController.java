package com.cskaoyan.controller.config;

import com.cskaoyan.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConfigController {

    //config/mall
    @RequestMapping("config/mall")
    @ResponseBody
    public ResponseVO mall(){
        ResponseVO<Object> vo = new ResponseVO<>();
        vo.setErrno(0);
        vo.setErrmsg("成功");
        vo.setData("8620b982-4c6c-4363-80f5-76d105b8e3dc");
        return vo;
    }
}
