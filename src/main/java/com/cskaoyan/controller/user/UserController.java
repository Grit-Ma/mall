package com.cskaoyan.controller.user;


import com.cskaoyan.bean.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin")
public class UserController {

    @RequestMapping("footprint")
    @RequestBody
    public ResponseVO userFootprint(){
        ResponseVO responseVO;
        return responseVO;
    }

}
