package com.cskaoyan.mall_admin.controller.config;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.config.ConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("admin/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    @RequiresPermissions("admin:config:mall:list")
    @GetMapping("mall")
    public ResponseVO listMall() {
        ResponseVO vo = new ResponseVO();
        Map<String, Object> data = configService.listMall();
        vo.setErrno(0);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequiresPermissions("admin:config:mall:updateConfigs")
    @PostMapping("mall")
    public ResponseVO updateMall(@RequestBody Map<String, Object> data) {
        ResponseVO vo = new ResponseVO();
        configService.updateConfig(data);
        vo.setErrno(0);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequiresPermissions("admin:config:express:list")
    @GetMapping("express")
    public ResponseVO listExpress() {
        ResponseVO vo = new ResponseVO();
        Map<String, Object> data = configService.listExpress();
        vo.setErrno(0);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequiresPermissions("admin:config:express:updateConfigs")
    @PostMapping("express")
    public ResponseVO updateExpress(@RequestBody Map<String, Object> data) {
        ResponseVO vo = new ResponseVO();
        configService.updateConfig(data);
        vo.setErrno(0);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequiresPermissions("admin:config:order:list")
    @GetMapping("order")
    public ResponseVO listOrder() {
        ResponseVO vo = new ResponseVO();
        Map<String, Object> data = configService.listOrder();
        vo.setErrno(0);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequiresPermissions("admin:config:order:updateConfigs")
    @PostMapping("order")
    public ResponseVO updateOrder(@RequestBody Map<String, Object> data) {
        ResponseVO vo = new ResponseVO();
        configService.updateConfig(data);
        vo.setErrno(0);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequiresPermissions("admin:config:wx:list")
    @GetMapping("wx")
    public ResponseVO listWx() {
        ResponseVO vo = new ResponseVO();
        Map<String, Object> data = configService.listWx();
        vo.setErrno(0);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequiresPermissions("admin:config:wx:updateConfigs")
    @PostMapping("wx")
    public ResponseVO updateWx(@RequestBody Map<String, Object> data) {
        ResponseVO vo = new ResponseVO();
        configService.updateConfig(data);
        vo.setErrno(0);
        vo.setErrmsg("成功");
        return vo;
    }


}
