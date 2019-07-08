package com.cskaoyan.mall_wx.service.zhe.impl;

import com.cskaoyan.bean.wx.collect.WxCollect;
import com.cskaoyan.bean.wx.pagedata.CollectPageData;
import com.cskaoyan.mall_wx.service.zhe.WxCollectService;
import com.cskaoyan.mapper.CollectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxCollectServiceImpl implements WxCollectService {
    @Autowired
    CollectMapper collectMapper;
    @Override
    public CollectPageData collectList(int page, int size,Integer type,Integer userId) {
        PageHelper.startPage(page,size);
        List<WxCollect> list = collectMapper.selectCollectByTypeAndUserId(type,userId);
        PageInfo pageInfo = new PageInfo(list);
        return new CollectPageData(pageInfo.getList(),pageInfo.getTotal());
    }
}
