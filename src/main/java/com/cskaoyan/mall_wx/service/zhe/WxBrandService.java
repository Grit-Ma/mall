package com.cskaoyan.mall_wx.service.zhe;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.wx.pagedata.BrandPageData;

public interface WxBrandService {
    BrandPageData brandList(int page, int size);

    Brand brandDetail(Integer id);
}
