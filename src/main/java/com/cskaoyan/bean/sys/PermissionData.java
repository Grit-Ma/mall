package com.cskaoyan.bean.sys;

import java.util.List;

public class PermissionData {
    List<SystemPermission> systemPermissions;
    List<String> assignedPermissions;

    public PermissionData() {
    }

    public PermissionData(List<SystemPermission> systemPermissions, List<String> assignedPermissions) {
        this.systemPermissions = systemPermissions;
        this.assignedPermissions = assignedPermissions;
    }

    public List<SystemPermission> getSystemPermissions() {
        return systemPermissions;
    }

    public void setSystemPermissions(List<SystemPermission> systemPermissions) {
        this.systemPermissions = systemPermissions;
    }

    public List<String> getAssignedPermissions() {
        return assignedPermissions;
    }

    public void setAssignedPermissions(List<String> assignedPermissions) {
        this.assignedPermissions = assignedPermissions;
    }


}
