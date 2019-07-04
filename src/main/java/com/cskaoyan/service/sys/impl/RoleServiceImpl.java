package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.Label;
import com.cskaoyan.bean.sys.Role;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.sys.RoleMapper;
import com.cskaoyan.service.sys.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;


    @Override
    public PageData selectAllRoleOptions(int page, int limit) {
        PageHelper.startPage(page,limit);
        List<Label> roles = roleMapper.selectAllRoleOptions();
        PageInfo<Label> pageInfo = new PageInfo<>(roles);
        PageData<Object> pageData = new PageData<>();
        pageData.setTotal(roles.size());
        pageData.setItems(pageInfo.getList());
        return pageData;
    }
}
