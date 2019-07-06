package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.Permission;
import com.cskaoyan.bean.sys.PermissionExample;
import com.cskaoyan.mapper.sys.PermissionMapper;
import com.cskaoyan.service.sys.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<String> selectPermissions(int roleId) {
        return permissionMapper.selectPermissions(roleId);
    }

    @Override
    @Transactional
    public int updateRolePermissions(int roleId, List<String> permissions) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andPermissionNotIn(permissions);
        Permission permission = new Permission();
        permission.setDeleted(false);
        permission.setUpdateTime(new Date());
        permissionMapper.updateByExampleSelective(permission, example);

        for(String s:permissions){
            PermissionExample e = new PermissionExample();
            e.createCriteria().andPermissionEqualTo(s).andRoleIdEqualTo(roleId);
            List<Permission> permissions1 = permissionMapper.selectByExample(e);
            if(permissions1.isEmpty()){
                Permission p = new Permission(roleId, s, new Date(), new Date(), false);
                permissionMapper.insertSelective(p);
            }
        }
        return 1;
    }
}
