package com.cskaoyan.mall_wx.service.zhe;

import com.cskaoyan.bean.wx.pagedata.CollectPageData;

public interface WxCollectService {
    CollectPageData collectList(int page, int size,Integer type,Integer userId);
}
