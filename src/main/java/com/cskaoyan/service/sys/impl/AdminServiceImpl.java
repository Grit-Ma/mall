package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.sys.AdminMapper;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public PageData selectAll(int page, int limit) {
        List<Admin> admins = adminMapper.selectAll();
        return WrapTool.setPageData(page,limit,admins);
    }

    @Override
    public int addAdmin(Admin admin) {
        return adminMapper.insert(admin);
    }

    @Override
    public PageData fuzzyQueryByName(int page,int limit,String username) {
        List<Admin> admins = adminMapper.fuzzyQueryByName(username);
        return WrapTool.setPageData(page,limit,admins);
    }

    @Override
    public int updateByPrimaryKey(Admin record) {
        return adminMapper.updateByPrimaryKey(record);
    }


}
