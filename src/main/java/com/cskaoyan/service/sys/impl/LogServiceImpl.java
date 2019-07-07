package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.LogType;
import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.Log;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.sys.LogMapper;
import com.cskaoyan.service.sys.LogService;
import com.cskaoyan.tool.WrapTool;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogMapper logMapper;


    private String action(Integer type) {
        switch (type){
            case 1:return "登陆";
            case 0:return "退出";
            default:return "其他";
        }
    }

    @Override
    public int addLog(Admin admin, Integer adminLog, boolean b) {
        String action=action(adminLog);
        Log log = new Log(admin.getUsername(), admin.getLastLoginIp(), adminLog, action, true,null,null,new Date(),new Date(),b );
        return logMapper.insert(log);
    }

    @Override
    public PageData fuzzyQueryByName(int page, int limit, String name, String sort, String order) {
        List<Log> logs = logMapper.fuzzyQueryByName(name,sort,order);
        return WrapTool.setPageData(page,limit,logs);
    }
}
