package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.AdminExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.sys.AdminMapper;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public int delete(Admin admin) {
        admin.setDeleted(true);
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public PageData selectAll(int page, int limit) {
        List<Admin> admins = adminMapper.selectAll();
        return WrapTool.setPageData(page, limit, admins);
    }

    @Override
    public List<Admin> selectAll() {
        return adminMapper.selectAll();
    }

    @Override
    public int addAdmin(Admin admin) {
        return adminMapper.insert(admin);
    }


    @Override
    public PageData fuzzyQueryByName(int page, int limit, String username, String sort, String order) {
        List<Admin> admins = adminMapper.fuzzyQueryByName(username, sort, order);
        return WrapTool.setPageData(page, limit, admins);
    }

    @Override
    public int updateByPrimaryKey(Admin record) {
        return adminMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Admin selectAdmin(Admin admin) {
        return adminMapper.selectByPrimaryKey(admin.getId());
    }


    //    当角色删除时，要关联删除admin对应的角色，调用到此方法
    @Override
    public void updateRoleIds(Integer roleId) {
        List<Admin> admins = adminMapper.fuzzyQueryByName("", null, null);
        for (Admin admin : admins) {
            int[] roleIds = admin.getRoleIds();
            ArrayList<Integer> ints = new ArrayList<>();
            for (int i : roleIds) {
                if (i != roleId) {
                    ints.add(i);
                }
            }
            int[] ints1 = new int[ints.size()];
            for (int i = 0; i < ints.size(); i++) {
                ints1[i] = ints.get(i);
            }
            admin.setRoleIds(ints1);
            adminMapper.updateByPrimaryKeySelective(admin);
        }
    }

    @Override
    public boolean adminNotExist(Admin admin) {
        String username = admin.getUsername();
        AdminExample e = new AdminExample();
        e.createCriteria().andUsernameEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(e);
        if (admins.isEmpty()||admin==null)
            return true;
        return false;
    }

    @Override
    public Admin selectByName(String username) {
        AdminExample e = new AdminExample();
        e.createCriteria().andUsernameEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(e);
        return admins.get(0);
    }


}
