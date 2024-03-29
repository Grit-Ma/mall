package com.cskaoyan.mall_admin.service.sys;

import com.cskaoyan.bean.sys.Label;
import com.cskaoyan.bean.sys.Role;
import com.cskaoyan.bean.vo.PageData;

import java.util.List;


public interface RoleService {
    List<Label> selectAllRoleOptions();

    PageData fuzzyQueryByName(int page, int limit, String name, String sort, String order);

    int updateRole(Role role);

    boolean checkName(Role role);

    int delete(Role role);

    int createRole(Role role);

    List<String> queryByIds(int[] roleIds);
}
