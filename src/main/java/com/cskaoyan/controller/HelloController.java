package com.cskaoyan.controller;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.LoginMessage;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.LogService;
import com.cskaoyan.service.sys.PermissionService;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.tool.LogRecording;
import com.cskaoyan.tool.WrapTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
public class HelloController {
    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;
    @Autowired
    LogService logService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    LogRecording logRecording;

    @RequestMapping("auth/login")
    public HashMap hello(@RequestBody LoginMessage loginMessage, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        try {
        subject.login(new UsernamePasswordToken(loginMessage.getUsername(), loginMessage.getPassword()));
        //执行登陆一系列操作
        logRecording.loginAction(loginMessage, request);
        return WrapTool.setResponseSuccess(subject.getSession().getId());//返回token
        } catch (Exception e) {
            return WrapTool.setResponseFailure(501, "登陆错误");
        }
    }

    @RequiresAuthentication
    @GetMapping("auth/info")
    public HashMap hello2() {
        HashMap data = getPermissionDatas();
        return WrapTool.setResponseSuccess(data);
    }

    @RequiresAuthentication
    @PostMapping("auth/logout")
    public HashMap logout() {
        Subject subject = SecurityUtils.getSubject();
        logRecording.logoutAction();
        subject.logout();
        return WrapTool.setResponseSuccessWithNoData();
    }


    //获得当前对象的所有权限
    public HashMap getPermissionDatas() {
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        int[] roleIds = admin.getRoleIds();
        List<String> roles = roleService.queryByIds(roleIds); //注意得到的是role"名字"的集合

        //获得所有详细权限的list
        List<String> permissions = new ArrayList<>();
        for (int i = 0; i < roleIds.length; i++) {
            List<String> selectPermissions = permissionService.selectPermissions(roleIds[i]);
            permissions.addAll(selectPermissions);
        }
        data.put("roles", roles);
        data.put("perms", permissions);
        return data;
    }

    @RequestMapping("dashboard")
    @ResponseBody
    public ResponseVO dashboard(){
        ResponseVO<Object> vo = new ResponseVO<>();
        vo.setData(adminService.getDashBoard());
        vo.setErrmsg("成功");
        return vo;
    }

}
