package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/admin/admin/list")
    @ResponseBody
    public ResponseVO<PageData> queryAdmin(int page, int limit, String username) {
        PageData pageData = new PageData();
        if (username != null) {
            pageData = adminService.fuzzyQueryByName(page, limit, username);
        } else {
            pageData = adminService.selectAll(page, limit);
        }
        return WrapTool.setResponseSuccess(pageData);
    }


    @RequestMapping("/admin/role/options")
    @ResponseBody
    public ResponseVO<PageData> queryRoles(int page, int limit) {
        PageData pageData = roleService.selectAllRoleOptions(page, limit);
        return WrapTool.setResponseSuccess(pageData);
    }


    @RequestMapping("admin/admin/create")
    @ResponseBody
    public ResponseVO<Admin> createAdmin(@Validated Admin admin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            responseVo.setErrorNo(1);
//            FieldError fieldError = bindingResult.getFieldError();
//            //校验通过的成员变量名
//            String field = fieldError.getField();
//            String defaultMessage = fieldError.getDefaultMessage();
//            responseVo.setMessage(defaultMessage);
//            return responseVo;
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError o : allErrors) {
                String defaultMessage = o.getDefaultMessage();
                WrapTool.setResponseFailure(601, defaultMessage);
            }
        }
        int i = adminService.addAdmin(admin);
        if (i == 1) {
            return WrapTool.setResponseSuccess(admin);
        } else {
            return WrapTool.setResponseFailure(400, "添加失败！");
        }
    }

    @RequestMapping("admin/admin/update")
    @ResponseBody
    public ResponseVO<Admin> updateAdmin(@Validated Admin admin, BindingResult bindingResult) {
        int i = adminService.updateByPrimaryKey(admin);
        if (i == 1) {
            return WrapTool.setResponseSuccess(admin);
        }else {
            return WrapTool.setResponseFailure(400,"编辑失败！");
        }
    }

}
