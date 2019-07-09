package com.cskaoyan.mall_wx.service.xfor.impl;

import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.RegionExample;
import com.cskaoyan.bean.mallmanage.region.Province;
import com.cskaoyan.mall_wx.service.xfor.WxRegionService;
import com.cskaoyan.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxRegionServiceImpl implements WxRegionService {
    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Region> getRegionList(int pid) {
        RegionExample regionExample = new RegionExample();
        regionExample.createCriteria().andPidEqualTo(pid);
        List<Region> regionList = regionMapper.selectByExample(regionExample);
        return regionList;
    }
}
