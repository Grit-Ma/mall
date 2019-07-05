package com.cskaoyan.service.sys;

import com.cskaoyan.bean.sys.Label;
import com.cskaoyan.bean.vo.PageData;

import java.util.List;


public interface RoleService {
    List<Label> selectAllRoleOptions();
}
