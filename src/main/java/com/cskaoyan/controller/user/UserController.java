package com.cskaoyan.controller.user;


import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin")
public class UserController {

    @Autowired
    UserService userService;

    //会员足迹(footprint)list
    @RequestMapping("footprint")
    public ResponseVO userFootprint(){
        return userService.footprintList();
    }

}
