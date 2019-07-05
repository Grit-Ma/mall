package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.service.sys.LogService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class LogController {

    @Autowired
    LogService logService;

    @RequestMapping("/log/list")
    @ResponseBody
    public HashMap queryAdmin(@RequestParam("page") int page, @RequestParam("limit")int limit, String name,String sort,String order) {
        PageData pageData = logService.fuzzyQueryByName(page, limit, name, sort, order);
        return WrapTool.setResponseSuccess(pageData);
    }

}
