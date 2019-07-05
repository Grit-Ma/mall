package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.sys.Label;
import com.cskaoyan.bean.sys.Log;
import com.cskaoyan.bean.sys.Role;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    AdminService adminService;

    @RequestMapping("/role/list")
    @ResponseBody
    public HashMap queryRolesList(@RequestParam("page") int page, @RequestParam("limit") int limit, String name,String sort,String order) {
        PageData pageData = roleService.fuzzyQueryByName(page, limit, name,sort,order);
        return WrapTool.setResponseSuccess(pageData);
    }


    @RequestMapping("role/options")
    @ResponseBody
    public HashMap queryRoles() {
        List<Label> labels = roleService.selectAllRoleOptions();
        return WrapTool.setResponseSuccess(labels);
    }

    @RequestMapping("/role/update")
    @ResponseBody
    public HashMap updateRoles(@RequestBody Role role) {
        role.setUpdateTime(new Date());
        int i = roleService.updateRole(role);
        if (i == 1) {
            return WrapTool.setResponseSuccess(role);
        } else if (i == 3) {
            return WrapTool.setResponseFailure(502, "系统内部错误");
        } else {
            return WrapTool.setResponseFailure(2, "更新失败！");
        }
    }

    @RequestMapping("/role/delete")
    @ResponseBody
    public HashMap deleteAdmin(@RequestBody Role role, BindingResult bindingResult) {
        int i = roleService.delete(role);
        if (i == 1) {
            adminService.updateRoleIds(role.getId());
            return WrapTool.setResponseSuccessWithNoData();
        } else {
            return WrapTool.setResponseFailure(3, "删除失败！");
        }
    }

    @RequestMapping("role/create")
    @ResponseBody
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
        return WrapTool.setResponseFailure(1, "添加失败！");
    }


//    @RequestMapping("/role/permissions")
//    @ResponseBody
//    public HashMap permissionsRole(int roleId){
//
//    }
}
