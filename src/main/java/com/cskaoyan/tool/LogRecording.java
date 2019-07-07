package com.cskaoyan.tool;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.LoginMessage;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.cskaoyan.bean.LogType.ADMIN_LOGIN;

@Component("logRecord")
public class LogRecording {

    @Autowired
    AdminService adminService;
    @Autowired
    LogService logService;

    public static LogRecording logRecording;

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
