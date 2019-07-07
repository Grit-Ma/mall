package com.cskaoyan.controller;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.LoginMessage;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.PermissionService;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.tool.IpUtil;
import com.cskaoyan.tool.WrapTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class HelloController {
    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    //admin/auth/login   模拟登录
    @RequestMapping("auth/login")
    public HashMap hello(@RequestBody LoginMessage loginMessage, HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(loginMessage.getUsername(),loginMessage.getPassword()));
            Admin admin=adminService.selectByName(loginMessage.getUsername());
            admin.setLastLoginTime(new Date());
            admin.setLastLoginIp(IpUtil.getIpAddr(request));
            adminService.updateByPrimaryKey(admin);

            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("token", subject.getSession().getId());
            return WrapTool.setResponseSuccess(result);
        }catch (Exception e)
        {
            return WrapTool.setResponseFailure(404,"登陆错误");
        }
    }

//    @RequiresAuthentication
    @GetMapping("auth/info")
    public HashMap hello2(String token){
//        Subject subject = SecurityUtils.getSubject();
//        Admin admin1 = (Admin) subject.getPrincipal();
        Admin admin=adminService.selectByName("admin123");
        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        int[] roleIds = admin.getRoleIds();
        List<String> roles=roleService.queryByIds(roleIds); //注意得到的是role"名字"的集合

//        //获得所有详细权限的list
//        List<String> permissions = new ArrayList<>();
//        for (int i = 0; i <roleIds.length ; i++) {
//            List<String> selectPermissions = permissionService.selectPermissions(roleIds[i]);
//            permissions.addAll(selectPermissions);
//        }
        data.put("roles", roles);
        data.put("perms", "*");
        return WrapTool.setResponseSuccess(data);
    }

}
