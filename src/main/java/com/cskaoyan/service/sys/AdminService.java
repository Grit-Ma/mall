package com.cskaoyan.service.sys;


import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.vo.PageData;



public interface AdminService {

    int delete(Admin admin);

    public PageData selectAll(int page, int limit);

    public int addAdmin(Admin admin);

    public PageData fuzzyQueryByName(int page, int limit,String username,String sort,String order);

    int updateByPrimaryKey(Admin record);

    Admin selectAdmin(Admin admin);

    void updateRoleIds(Integer roleId);
}
