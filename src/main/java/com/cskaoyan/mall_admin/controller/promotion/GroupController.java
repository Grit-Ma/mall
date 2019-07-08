package com.cskaoyan.mall_admin.controller.promotion;

import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GrouponRules;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.promotion.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * created by ZengWei
 * on 2019/7/6
 */

@Controller
public class GroupController {

    @Autowired
    GroupService groupService;

    @RequestMapping("groupon/list")
    @ResponseBody
    public ResponseVO list(int page, int limit, String sort, String order, Integer goodsId) {
        ResponseVO vo = new ResponseVO();
        PageData data = groupService.getGroupListData(page, limit, sort, order, goodsId);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("groupon/update")
    @ResponseBody
    public ResponseVO update(@RequestBody GrouponRules grouponRules) {
        ResponseVO vo = new ResponseVO();
        vo = groupService.updateGroup(grouponRules);
        return vo;
    }

    @RequestMapping("groupon/delete")
    @ResponseBody
    public ResponseVO delete(@RequestBody GrouponRules grouponRules) {
        ResponseVO vo = new ResponseVO();
        vo = groupService.deleteGroup(grouponRules);
        return vo;
    }

    @RequestMapping("groupon/create")
    @ResponseBody
    public ResponseVO insert(@RequestBody GrouponRules grouponRules) {
        ResponseVO vo = new ResponseVO();
        int id = grouponRules.getGoodsId();
        Goods goods = groupService.getGoodsById(id);
        grouponRules.setGoodsName(goods.getName());
        grouponRules.setAddTime(new Date());
        grouponRules.setUpdateTime(new Date());
        grouponRules.setPicUrl(goods.getPicUrl());
        System.out.println(grouponRules);
        vo = groupService.insertGroup(grouponRules);
        return vo;
    }

    @RequestMapping("groupon/listRecord")
    @ResponseBody
    public ResponseVO list(int page, int limit, String sort, String order) {
        ResponseVO vo = new ResponseVO();
        PageData data = groupService.getListRecordData(page, limit, sort, order);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }
}
