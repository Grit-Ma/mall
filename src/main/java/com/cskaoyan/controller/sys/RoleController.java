package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.sys.*;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.PermissionService;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.service.sys.SystemPermissionService;
import com.cskaoyan.tool.WrapTool;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    AdminService adminService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    SystemPermissionService systemPermissionService;

    @RequiresPermissions(value = "admin:role:list")
    @GetMapping("/role/list")
    public HashMap queryRolesList(@RequestParam(value = "page",defaultValue = "1")int page,
                                  @RequestParam(value = "limit",defaultValue = "20")int limit,
                                  String name, String sort, String order) {
        PageData pageData = roleService.fuzzyQueryByName(page, limit, name, sort, order);
        return WrapTool.setResponseSuccess(pageData);
    }

    @RequiresPermissions(value = "admin:role:read")
    @GetMapping("role/options")
    public HashMap queryRoles() {
        List<Label> labels = roleService.selectAllRoleOptions();
        return WrapTool.setResponseSuccess(labels);
    }

    @RequiresPermissions(value = "admin:role:update")
    @PostMapping("/role/update")
    public HashMap updateRoles(@RequestBody Role role) {
        role.setUpdateTime(new Date());
        int i = roleService.updateRole(role);
        if (i == 1) {
            return WrapTool.setResponseSuccess(role);
        } else if (i == 3) {
            return WrapTool.setResponseFailure(502, "系统内部错误");
        } else {
            return WrapTool.setResponseFailure(401, "参数不对");
        }
    }

    @RequiresPermissions(value = "admin:role:delete")
    @PostMapping("/role/delete")
    public HashMap deleteAdmin(@RequestBody Role role, BindingResult bindingResult) {
        int i = roleService.delete(role);
        if (i == 1) {
            adminService.updateRoleIds(role.getId());
            return WrapTool.setResponseSuccessWithNoData();
        } else {
            return WrapTool.setResponseFailure(401, "参数不对");
        }
    }

    @RequiresPermissions(value = "admin:role:create")
    @PostMapping("role/create")
    public HashMap createRole(@RequestBody Role role) {
        role.setAddTime(new Date());
        role.setUpdateTime(new Date());
        role.setDeleted(false);
        role.setEnabled(true);
        int i = roleService.createRole(role);
        if (i == 1) {
            return WrapTool.setResponseSuccess(role);
        } else if (i == 3) {
            return WrapTool.setResponseFailure(502, "系统内部错误");
        }
        return WrapTool.setResponseFailure(401, "参数不对");
    }

    @RequiresPermissions(value = "admin:role:permission:update")
    @PostMapping("role/permissions")
    public HashMap permissionsToUpdate(@RequestBody UpdatePermission updatePermission) {
        int i = permissionService.updateRolePermissions(updatePermission.getRoleId(), updatePermission.getPermissions());
        if (i == 1) {
            return WrapTool.setResponseSuccessWithNoData();
        }
        return WrapTool.setResponseFailure(401, "参数不对");
    }


    @RequiresPermissions(value = "admin:role:permission:get")
    @GetMapping("role/permissions")
    public HashMap permissionsRole(@RequestParam("roleId") int roleId) {
        List<String> assignedPermissions = permissionService.selectPermissions(roleId);
        List<SystemPermission> systemPermissions = systemPermissionService.queryAllSystemPermissions();
        return WrapTool.setResponseSuccess(new PermissionData(systemPermissions,assignedPermissions));
    }
}
