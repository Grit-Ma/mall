package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.mapper.sys.AdminMapper;
import com.cskaoyan.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminMapper adminMapper;

    @RequestMapping("/admin/admin/list")
    @ResponseBody
    public QueryVo queryAdmin(int page,int limit){
        List<Admin> list= adminMapper.selectAll();
    }
}
