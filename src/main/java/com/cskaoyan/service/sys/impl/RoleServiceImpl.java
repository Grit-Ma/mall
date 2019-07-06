package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.*;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.sys.RoleMapper;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;


    @Override
    public List<Label> selectAllRoleOptions() {
        List<Label> roles = roleMapper.selectAllRoleOptions();
        return roles;
    }

    @Override
    public PageData fuzzyQueryByName(int page, int limit,String name,String sort,String order) {
        List<Log> logs = roleMapper.fuzzyQueryByName(name,sort,order);
        return WrapTool.setPageData(page,limit,logs);
    }

    @Override
    public int updateRole(Role role) {
        if (checkName(role)) {
            return roleMapper.updateByPrimaryKeySelective(role);
        } else {
            return 3;
        }
    }

//    检验角色名字是否有相同的，但是要注意是在未删除的（deleted=false）里面判断；数据库中应当取消name_unique
    @Override
    public boolean checkName(Role role) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(role.getName()).andDeletedEqualTo(false);
        if(role.getId()!=null){
            criteria.andIdNotEqualTo(role.getId());  //注意当仅仅更新操作，通过自身id判断相同名字的是不是自己
        }
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if(roles.isEmpty()){
            return true;
        }else return false;
    }

    @Override
    public int delete(Role role) {
        role.setDeleted(true);
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int createRole(Role role) {
        if(checkName(role)) {
            return roleMapper.insert(role);
        }else {
            return 3;
        }
    }

}
