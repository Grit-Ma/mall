package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.RoleService;
import com.cskaoyan.tool.RegexUtil;
import com.cskaoyan.tool.WrapTool;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import static com.cskaoyan.bean.ResponseFailureCode.*;


@RestController
@Api(tags = "sysAdminTags", value = "AdminValue")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;

    @RequiresPermissions(value = "admin:admin:list")
    @GetMapping("admin/list")
    public HashMap queryAdmin(@RequestParam(value = "page",defaultValue = "1") int page,
                              @RequestParam(value = "limit",defaultValue = "20") int limit,
                              String username, String sort, String order) {
        PageData pageData;
        if (username != null) {
            pageData = adminService.fuzzyQueryByName(page, limit, username, sort, order);
        } else {
            pageData = adminService.selectAll(page, limit);
        }
        return WrapTool.setResponseSuccess(pageData);
    }

    @RequiresPermissions(value = "admin:admin:create")
    @PostMapping("admin/create")
    public HashMap createAdmin(@Validated @RequestBody Admin admin) {
        //先进行验证
        HashMap notValid = validateAdmin(admin);
        if (!notValid.isEmpty()) {
            return notValid;
        }
        admin.setAddTime(new Date());
        admin.setUpdateTime(new Date());
        admin.setDeleted(false);

        //进行密码加密
//        String password = admin.getPassword();
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodedPassword = encoder.encode(password);
//        admin.setPassword(encodedPassword);

        adminService.addAdmin(admin);
        return WrapTool.setResponseSuccess(admin);
    }

    @RequiresPermissions(value = "admin:admin:update")
    @PostMapping("admin/update")
    public HashMap updateAdmin(@Validated @RequestBody Admin admin) {
        HashMap notValid = validateAdmin(admin);
        if (!notValid.isEmpty()) {
            return notValid;
        }
        admin.setUpdateTime(new Date());
        adminService.updateByPrimaryKey(admin);
        return WrapTool.setResponseSuccess(admin);
    }

    @RequiresPermissions(value = "admin:admin:delete")
    @PostMapping("/admin/delete")
    public HashMap deleteAdmin(@RequestBody Admin admin) {
        Integer anotherAdminId = admin.getId();
        if (anotherAdminId == null) {
            return WrapTool.setResponseFailure(401, "参数不对");
        }
        // 管理员不能删除自身账号!!
        Subject subject = SecurityUtils.getSubject();
        Admin currentAdmin = (Admin) subject.getPrincipal();
        if (currentAdmin.getId().equals(anotherAdminId)) {
            return WrapTool.setResponseFailure(ADMIN_DELETE_NOT_ALLOWED, "管理员不能删除自己账号");
        }
        adminService.delete(admin);
        return WrapTool.setResponseSuccessWithNoData();
    }



    //在创建和更新账户之前先完成验证
    public HashMap validateAdmin(Admin admin) {
        String name = admin.getUsername();
        if (StringUtils.isEmpty(name) || !RegexUtil.isUsername(name)) {
            return WrapTool.setResponseFailure(ADMIN_INVALID_NAME, "管理员名称不符合规定");
        }
        String password = admin.getPassword();
        if (StringUtils.isEmpty(password) || password.length() < 6) {
            return WrapTool.setResponseFailure(ADMIN_INVALID_PASSWORD, "管理员密码长度不能小于6");
        }
        if (!adminService.adminNotExist(admin)) {
            return WrapTool.setResponseFailure(ADMIN_NAME_EXIST, "管理员已经存在");
        } else return new HashMap();
    }

}
