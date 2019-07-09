package com.cskaoyan.mall_admin.controller.stat;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.vo.StatVO;
import com.cskaoyan.mall_admin.service.stat.StatService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/stat")
public class StatController {

    @Autowired
    private StatService statService;

    @RequiresPermissions("admin:stat:user")
    @GetMapping("user")
    public ResponseVO statUser() {
        ResponseVO vo = new ResponseVO();
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVO statVo = new StatVO();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        vo.setData(statVo);
        return vo.ok(vo);
    }

    @RequiresPermissions("admin:stat:order")
    @GetMapping("order")
    public Object statOrder() {
        ResponseVO vo = new ResponseVO();
        List<Map> rows = statService.statOrder();
        String[] columns = new String[]{"day", "orders", "customers", "amount", "pcr"};
        StatVO statVo = new StatVO();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        vo.setData(statVo);

        return vo.ok(vo);
    }

    @RequiresPermissions("admin:stat:goods")
    @GetMapping("goods")
    public Object statGoods() {
        ResponseVO vo = new ResponseVO();
        List<Map> rows = statService.statGoods();
        String[] columns = new String[]{"day", "orders", "products", "amount"};
        StatVO statVo = new StatVO();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        vo.setData(statVo);
        return vo.ok(vo);
    }

}
