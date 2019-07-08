package com.cskaoyan.mall_admin.service.sys;

import java.util.List;

public interface PermissionService {

    List<String> selectPermissions(int roleId) ;

    int updateRolePermissions(int roleId,List<String> permissions);
}
