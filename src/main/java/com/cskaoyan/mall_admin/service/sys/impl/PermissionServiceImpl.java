package com.cskaoyan.mall_admin.service.sys.impl;

import com.cskaoyan.bean.sys.Permission;
import com.cskaoyan.bean.sys.PermissionExample;
import com.cskaoyan.mapper.sys.PermissionMapper;
import com.cskaoyan.mall_admin.service.sys.PermissionService;
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

        //当获取的权限list是空;对原先的已经授权的进行取消
        if(permissions==null||permissions.isEmpty()){
            example.createCriteria().andRoleIdEqualTo(roleId);
            permissionMapper.deleteByExample(example);
            return 1;
        }

        //对于原先有的，但是没有在更新后的权限列表里的，进行取消授权
        example= new PermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andPermissionNotIn(permissions);
        Permission permission = new Permission();
        permission.setDeleted(true);
        permission.setUpdateTime(new Date());
        permissionMapper.updateByExampleSelective(permission, example);


        //循环添加不在原先权限表中的权限
        for(String s:permissions){
            example = new PermissionExample();
            example.createCriteria().andPermissionEqualTo(s).andRoleIdEqualTo(roleId);
            List<Permission> permissions1 = permissionMapper.selectByExample(example);
            if(permissions1.isEmpty()){
                Permission p = new Permission(roleId, s, new Date(), new Date(), false);
                permissionMapper.insert(p);
            }
        }
        return 1;
    }
}
