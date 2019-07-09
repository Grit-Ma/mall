package com.cskaoyan.mall_wx.service.cdy;

import com.cskaoyan.bean.Address;

import java.util.List;

public interface WxAddressService {
    List<Address> queryByUid(Integer userId);
}
