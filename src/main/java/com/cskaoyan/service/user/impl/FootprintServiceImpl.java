package com.cskaoyan.service.user.impl;

import com.cskaoyan.bean.Footprint;
import com.cskaoyan.bean.FootprintExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.FootprintMapper;
import com.cskaoyan.service.user.FootprintService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FootprintServiceImpl implements FootprintService {

    @Autowired
    FootprintMapper footprintMapper;


    //会员足迹(footprint)list
    @Override
    public PageData footprintList(int page, int limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        List<Footprint> footprintList = footprintMapper.selectAll();
        PageInfo pageinfo = new PageInfo(footprintList);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }


    //会员足迹search
    @Override
    public PageData footprintSearch(int page, int limit, String sort, String order, Integer userId, Integer goodsId) {
        PageHelper.startPage(page,limit);
        List<Footprint> footprintList = footprintMapper.selectBysearch(userId, goodsId);
        PageInfo pageinfo = new PageInfo(footprintList);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }
}
