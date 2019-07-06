package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.SystemPermission;
import com.cskaoyan.mapper.sys.SystemPermissionMapper;
import com.cskaoyan.service.sys.SystemPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemPermissionServiceImpl implements SystemPermissionService {
    @Autowired
    SystemPermissionMapper systemPermissionMapper;

    @Override
    public List<SystemPermission> queryAllSystemPermissions() {
        return systemPermissionMapper.queryAllSystemPermissions();
    }
}
