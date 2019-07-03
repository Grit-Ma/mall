package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.mapper.sys.AdminMapper;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.vo.QueryData;
import com.cskaoyan.vo.QueryVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public QueryData<Admin> selectAll(int page,int limit) {
        PageHelper.startPage(page,limit);
        List<Admin> admins = adminMapper.selectAll();
        PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);
        QueryData<Admin> adminQueryData = new QueryData<>(admins.size(), adminPageInfo.getList());
        return adminQueryData;
    }
}
