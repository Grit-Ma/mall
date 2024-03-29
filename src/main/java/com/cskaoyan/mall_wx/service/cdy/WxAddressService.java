package com.cskaoyan.mall_wx.service.cdy;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.AddressPackage;

import java.util.List;

public interface WxAddressService {
    List<Address> queryByUid(Integer userId);

    AddressPackage query(Integer userId, Integer id);

    Integer update(Address address);

    Integer delete(Integer id);
}
