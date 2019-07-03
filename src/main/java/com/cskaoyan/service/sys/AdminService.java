package com.cskaoyan.service.sys;


import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.mapper.sys.AdminMapper;
import com.cskaoyan.vo.QueryData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface AdminService {

    QueryData<Admin> selectAll(int page, int limit);
}
