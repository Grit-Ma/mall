package com.cskaoyan.mall_wx.service.xfor;

import com.cskaoyan.bean.wx.xfor.CatalogList;
import com.cskaoyan.bean.wx.xfor.CurrentCatalogList;

public interface WxCatalogService {
    CatalogList getCatalogList();

    CurrentCatalogList getCurrentCatalogList(int id);
}
