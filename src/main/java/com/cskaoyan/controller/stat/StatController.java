package com.cskaoyan.controller.stat;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.vo.StatVO;
import com.cskaoyan.service.stat.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StatController {

    @Autowired
    private StatService statService;

    @GetMapping("stat/user")
    public ResponseVO statUser() {
        ResponseVO vo = new ResponseVO();
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVO statVo = new StatVO();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        vo.setData(statVo);
        vo.setErrmsg("成功");
        return vo;
    }

    @GetMapping("stat/order")
    public Object statOrder() {
        ResponseVO vo = new ResponseVO();
        List<Map> rows = statService.statOrder();
        String[] columns = new String[]{"day", "orders", "customers", "amount", "pcr"};
        StatVO statVo = new StatVO();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        vo.setData(statVo);
        vo.setErrmsg("成功");

        return vo;
    }

    @GetMapping("stat/goods")
    public Object statGoods() {
        ResponseVO vo = new ResponseVO();
        List<Map> rows = statService.statGoods();
        String[] columns = new String[]{"day", "orders", "products", "amount"};
        StatVO statVo = new StatVO();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        vo.setData(statVo);
        vo.setErrmsg("成功");

        return vo;
    }

}
