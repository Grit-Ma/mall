package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.Role;
import com.cskaoyan.mapper.sys.AdminMapper;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.vo.QueryData;
import com.cskaoyan.vo.QueryVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("/admin/admin/list")
    @ResponseBody
    public QueryVo queryAdmin(int page,int limit){
        QueryData<Admin> adminQueryData = adminService.selectAll(page, limit);
        return new QueryVo(0,adminQueryData,"成功");
    }


    @Autowired
    RoleService roleService;

    @RequestMapping("/admin/role/options")
    @ResponseBody
    public QueryVo queryRoles(){
        List<Role> roles =
    }
}
