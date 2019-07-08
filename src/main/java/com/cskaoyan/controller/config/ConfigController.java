package com.cskaoyan.controller.config;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("admin")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    @GetMapping("config/mall")
    public ResponseVO listMall() {
        ResponseVO vo = new ResponseVO();
        Map<String, Object> data = configService.listMall();
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }


    @PostMapping("/config/mall")
    public ResponseVO updateMall(@RequestBody Map<String, Object> data) {
        ResponseVO vo = new ResponseVO();
        configService.updateConfig(data);
        vo.setErrno(0);
        vo.setErrmsg("成功");
        return vo;
    }

    @GetMapping("config/express")
    public ResponseVO listExpress() {
        ResponseVO vo = new ResponseVO();
        Map<String, Object> data = configService.listExpress();
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }


    @PostMapping("/config/express")
    public ResponseVO updateExpress(@RequestBody Map<String, Object> data) {
        ResponseVO vo = new ResponseVO();
        configService.updateConfig(data);
        vo.setErrno(0);
        vo.setErrmsg("成功");
        return vo;
    }

    @GetMapping("config/order")
    public ResponseVO listOrder() {
        ResponseVO vo = new ResponseVO();
        Map<String, Object> data = configService.listOrder();
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }


    @PostMapping("/config/order")
    public ResponseVO updateOrder(@RequestBody Map<String, Object> data) {
        ResponseVO vo = new ResponseVO();
        configService.updateConfig(data);
        vo.setErrno(0);
        vo.setErrmsg("成功");
        return vo;
    }

    @GetMapping("config/wx")
    public ResponseVO listWx() {
        ResponseVO vo = new ResponseVO();
        Map<String, Object> data = configService.listWx();
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }


    @PostMapping("/config/wx")
    public ResponseVO updateWx(@RequestBody Map<String, Object> data) {
        ResponseVO vo = new ResponseVO();
        configService.updateConfig(data);
        vo.setErrno(0);
        vo.setErrmsg("成功");
        return vo;
    }


}
