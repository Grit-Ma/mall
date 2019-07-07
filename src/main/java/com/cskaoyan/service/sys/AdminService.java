package com.cskaoyan.service.sys;


import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.vo.PageData;

import java.util.List;


public interface AdminService {

    int delete(Admin admin);

    PageData selectAll(int page, int limit);

    List<Admin> selectAll();

    int addAdmin(Admin admin);

    PageData fuzzyQueryByName(int page, int limit,String username,String sort,String order);

    int updateByPrimaryKey(Admin record);

    Admin selectAdmin(Admin admin);

    void updateRoleIds(Integer roleId);

    boolean adminNotExist(Admin admin);

    Admin selectByName(String name);
}
