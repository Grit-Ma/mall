package com.cskaoyan.service.sys;


import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.Log;
import com.cskaoyan.bean.vo.PageData;

import java.util.List;

public interface LogService {

    int addLog(Admin admin, Integer adminLogin, boolean b);

    PageData fuzzyQueryByName(int page, int limit, String name, String sort, String order);
}
