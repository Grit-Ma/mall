package com.cskaoyan.mall_admin.service.mallManageService.impl;

import com.cskaoyan.bean.mallmanage.region.Province;
import com.cskaoyan.mapper.RegionMapper;
import com.cskaoyan.mall_admin.service.mallManageService.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    RegionMapper regionMapper;
    @Override
    public List<Province> regionList(){
        List<Province> list = regionMapper.selectRegion();
        return list;
    }
}
