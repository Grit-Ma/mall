package com.cskaoyan.mall_wx.service.zhe.impl;

import com.cskaoyan.bean.Collect;
import com.cskaoyan.bean.CollectExample;
import com.cskaoyan.bean.wx.collect.TypeAndValueId;
import com.cskaoyan.bean.wx.collect.WxCollect;
import com.cskaoyan.bean.wx.pagedata.CollectPageData;
import com.cskaoyan.mall_wx.service.zhe.WxCollectService;
import com.cskaoyan.mapper.CollectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public String collectAddOrDelete(TypeAndValueId typeAndValueId,Integer userId) {
        String s = "";
        Date date = new Date();
        CollectExample example = new CollectExample();
        example.createCriteria().andUserIdEqualTo(userId).andValueIdEqualTo(typeAndValueId.getValueId()).
                andTypeEqualTo(typeAndValueId.getType()).andDeletedEqualTo(false);
        List<Collect> collects = collectMapper.selectByExample(example);
        if (collects.size() == 1){
            Collect collect = collects.get(0);
            collect.setUpdateTime(date);
            collect.setDeleted(true);
            collectMapper.updateByPrimaryKeySelective(collect);
            s = "delete";
        }

        if(collects.size() == 0){
            Collect collect = new Collect();
            collect.setType(typeAndValueId.getType());
            collect.setValueId(typeAndValueId.getValueId());
            collect.setUserId(userId);
            collect.setAddTime(date);
            collect.setUpdateTime(date);
            collectMapper.insertSelective(collect);
            s = "add";
        }
        return s;
    }
}
