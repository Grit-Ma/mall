package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.Role;
import com.cskaoyan.mapper.sys.RoleMapper;
import com.cskaoyan.service.sys.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> selectAllRoleOptions() {
        return roleMapper.selectAllRoleOptions();
    }
}
