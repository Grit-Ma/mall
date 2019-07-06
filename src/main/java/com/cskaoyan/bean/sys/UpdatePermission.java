package com.cskaoyan.bean.sys;

import java.util.List;

public class UpdatePermission {
    List<String> permissions;
    int roleId;

    public UpdatePermission() {
    }

    public UpdatePermission(List<String> permissions, int roleId) {
        this.permissions = permissions;
        this.roleId = roleId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
