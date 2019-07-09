package com.cskaoyan.mall_wx.service.cdy;

import com.cskaoyan.bean.Region;

import java.util.List;

public interface WxRegionService {
    List<Region> queryByPid(Integer pid);
}
