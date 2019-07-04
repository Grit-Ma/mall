package com.cskaoyan.controller;

import com.cskaoyan.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class HelloController {
    //admin/auth/login   模拟登录
    @RequestMapping("auth/login")
    @ResponseBody
    public ResponseVO hello(){
        ResponseVO<Object> vo = new ResponseVO<>();
        vo.setErrno(0);
        vo.setErrmsg("成功");
        vo.setData("8620b982-4c6c-4363-80f5-76d105b8e3dc");
        return vo;
    }
    @RequestMapping("auth/info")
    @ResponseBody
    public ResponseVO hello2(){
        ResponseVO<Object> vo = new ResponseVO<>();
        vo.setErrno(0);
        vo.setErrmsg("成功");
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Object> roles = new ArrayList<>();
        roles.add("超级管理员");
        ArrayList<Object> perms = new ArrayList<>();
        perms.add("*");
        map.put("roles",roles);
        map.put("name","admin123");
        map.put("perms",perms);
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        vo.setData(map);
        return vo;
    }

}
