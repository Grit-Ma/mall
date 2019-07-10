package com.cskaoyan.mall_wx.service.xfor;

import com.cskaoyan.bean.wx.xfor.FootprintList;

import javax.servlet.http.HttpServletRequest;

public interface WxFootprintService {
    FootprintList getFootprintList(HttpServletRequest request, int page, int size);
}
