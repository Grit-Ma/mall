package com.cskaoyan.service.sys;


import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.vo.PageData;



public interface AdminService {

    public PageData selectAll(int page, int limit);

    public int addAdmin(Admin admin);

    public PageData fuzzyQueryByName(int page,int limit,String username);

    int updateByPrimaryKey(Admin record);
}
