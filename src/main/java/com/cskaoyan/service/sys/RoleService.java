package com.cskaoyan.service.sys;

import com.cskaoyan.bean.vo.PageData;


public interface RoleService {
    PageData selectAllRoleOptions(int page, int limit);
}
