package com.cskaoyan.mall_wx.service.login;

import com.cskaoyan.bean.wx.xfor.CatalogList;
import com.cskaoyan.bean.wx.xfor.CurrentCatalogList;

public interface CatalogService {
    CatalogList getCatalogList();

    CurrentCatalogList getCurrentCatalogList(int id);
}
