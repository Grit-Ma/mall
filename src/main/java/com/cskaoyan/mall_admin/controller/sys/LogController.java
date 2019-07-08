package com.cskaoyan.mall_admin.controller.sys;

import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mall_admin.service.sys.LogService;
import com.cskaoyan.tool.WrapTool;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class LogController {

    @Autowired
    LogService logService;

    @RequiresPermissions(value = "admin:log:list")
    @GetMapping("/log/list")
    public HashMap queryAdmin(@RequestParam(value = "page",defaultValue = "1")int page,
                              @RequestParam(value = "limit",defaultValue = "20")int limit,
                              String name,String sort,String order) {
        PageData pageData = logService.fuzzyQueryByName(page, limit, name, sort, order);
        return WrapTool.setResponseSuccess(pageData);
    }

}
