package com.cskaoyan.tool;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.LoginMessage;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.LogService;
import com.cskaoyan.service.sys.impl.AdminServiceImpl;
import com.cskaoyan.service.sys.impl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.cskaoyan.bean.LogType.ADMIN_LOGIN;

//@Component("logRecord")
@SpringBootApplication
public class LogRecording {

    @Autowired
    AdminService adminService;
    @Autowired
    LogService logService;

    public static LogRecording logRecording;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        logRecording = this;
        logRecording.adminService = this.adminService;
        logRecording.logService = this.logService;
    }

    @Transactional
    public void loginAction(LoginMessage loginMessage, HttpServletRequest request) {
        Admin admin = adminService.selectByName(loginMessage.getUsername());
        admin.setLastLoginTime(new Date());
        admin.setLastLoginIp(IpUtil.getIpAddr(request));
        try {
            adminService.updateByPrimaryKey(admin);
            logService.addLog(admin, ADMIN_LOGIN,false);
        }catch (Exception e){
            logService.addLog(admin,ADMIN_LOGIN,true);
        }
    }

}
