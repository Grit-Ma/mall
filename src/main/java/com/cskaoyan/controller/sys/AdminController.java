package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.tool.WrapTool;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Controller
@Api(tags = "sysAdminTags",value = "AdminValue")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;

    @RequestMapping("admin/list")
    @ResponseBody
    public HashMap queryAdmin(@RequestParam("page") int page, @RequestParam("limit")int limit, String username,String sort,String order) {
        PageData pageData;
        if (username != null) {
            pageData = adminService.fuzzyQueryByName(page, limit, username,sort,order);
        } else {
            pageData = adminService.selectAll(page, limit);
        }
        return WrapTool.setResponseSuccess(pageData);
    }



    @RequestMapping("admin/create")
    @ResponseBody
    public HashMap createAdmin(@Validated @RequestBody Admin admin, BindingResult bindingResult) {
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
        admin.setAddTime(new Date());
        admin.setUpdateTime(new Date());
        admin.setDeleted(false);
        int i = adminService.addAdmin(admin);
        if (i == 1) {
            return WrapTool.setResponseSuccess(admin);
        } else {
            return WrapTool.setResponseFailure(1, "添加失败！");
        }
    }

    @RequestMapping("admin/update")
    @ResponseBody
    public HashMap updateAdmin(@Validated @RequestBody Admin admin, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            List<ObjectError> allErrors = bindingResult.getAllErrors();
//            for (ObjectError o : allErrors) {
//                String defaultMessage = o.getDefaultMessage();
//                WrapTool.setResponseFailure(601, defaultMessage);
//            }
//        }
        admin.setUpdateTime(new Date());
        int i = adminService.updateByPrimaryKey(admin);
        if (i == 1) {
            return WrapTool.setResponseSuccess(admin);
        }else {
            return WrapTool.setResponseFailure(2,"更新失败！");
        }
    }

    @RequestMapping("/admin/delete")
    @ResponseBody
    public HashMap deleteAdmin(@RequestBody Admin admin) {
        int i = adminService.delete(admin);
        if (i == 1) {
            return WrapTool.setResponseSuccessWithNoData();
        }else {
            return WrapTool.setResponseFailure(3,"删除失败！");
        }
    }


}
