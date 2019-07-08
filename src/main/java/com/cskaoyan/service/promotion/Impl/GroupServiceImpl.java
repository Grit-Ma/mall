package com.cskaoyan.service.promotion.Impl;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.GrouponMapper;
import com.cskaoyan.mapper.GrouponRulesMapper;
import com.cskaoyan.service.promotion.GroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.System;
import java.util.ArrayList;
import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/6
 */

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GrouponMapper grouponMapper;

    @Override
    public PageData getGroupListData(int page, int limit, String sort, String order, Integer goodsId) {
        PageHelper.startPage(page,limit);
        GrouponRulesExample example = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria = example.createCriteria();
        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        example.setOrderByClause(sort+" "+order);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(example);
        PageInfo<GrouponRules> pageinfo = new PageInfo(grouponRules);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public ResponseVO updateGroup(GrouponRules grouponRules) {
        ResponseVO vo = new ResponseVO();
        try{
            grouponRulesMapper.updateByPrimaryKey(grouponRules);
            vo.setErrmsg("修改成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("修改失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public ResponseVO deleteGroup(GrouponRules grouponRules) {
        ResponseVO vo = new ResponseVO();
        try{
            grouponRulesMapper.deleteByPrimaryKey(grouponRules.getId());
            vo.setErrmsg("删除成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("删除失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public ResponseVO insertGroup(GrouponRules grouponRules) {
        ResponseVO vo = new ResponseVO();
        try{
            grouponRulesMapper.insert(grouponRules);
            vo.setErrmsg("添加成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("添加失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public Goods getGoodsById(int id) {
        return goodsMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageData getListRecordData(int page, int limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        List<GroupActivity> groupActivities = new ArrayList<>();
        List<Groupon> g =  grouponMapper.selectByExample(new GrouponExample());
        for (int i = 0; i < g.size() ; i++) {
            GroupActivity ga = new GroupActivity();
            groupActivities.add(ga);
            ga.setGroupon(g.get(i));
            GrouponRules gr = grouponRulesMapper.selectByPrimaryKey(g.get(i).getRulesId());
            ga.setRules(gr);
            Goods gd = goodsMapper.selectByPrimaryKey(gr.getGoodsId());
            ga.setGoods(gd);
            Integer[] sg = new Integer[0];
            ga.setSubGroupons(sg);
        }
        System.out.println(groupActivities);
        PageInfo<GroupActivity> pageinfo = new PageInfo(groupActivities);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }
}
