package com.cskaoyan.mall_wx.service.cdy.impl;

import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.RegionExample;
import com.cskaoyan.mall_wx.service.cdy.WxRegionService;
import com.cskaoyan.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxRegionServiceImpl implements WxRegionService {

    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Region> queryByPid(Integer pid) {
        RegionExample regionExample = new RegionExample();
        regionExample.or().andPidEqualTo(pid);
        return regionMapper.selectByExample(regionExample);
    }
}
