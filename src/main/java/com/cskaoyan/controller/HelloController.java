package com.cskaoyan.controller;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.LoginMessage;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.LogService;
import com.cskaoyan.service.sys.PermissionService;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.tool.IpUtil;
import com.cskaoyan.tool.LogRecording;
import com.cskaoyan.tool.WrapTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.cskaoyan.bean.LogType.ADMIN_LOGIN;

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
            return WrapTool.setResponseSuccess(subject.getSession().getId());
        } catch (Exception e) {
            return WrapTool.setResponseFailure(404, "登陆错误");
        }
    }


    @RequiresAuthentication
    @GetMapping("auth/info")
    public HashMap hello2(String token) {
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        Map<String, Object> data = new HashMap<>();
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
        return WrapTool.setResponseSuccess(data);
    }

    @RequiresAuthentication
    @PostMapping("auth/logout")
    public HashMap logout(){
        Subject subject = SecurityUtils.getSubject();
        logRecording.logoutAction();
        subject.logout();
        return WrapTool.setResponseSuccessWithNoData();
    }

}
