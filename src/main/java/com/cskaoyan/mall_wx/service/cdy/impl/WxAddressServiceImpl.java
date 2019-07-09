package com.cskaoyan.mall_wx.service.cdy.impl;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.AddressExample;
import com.cskaoyan.mall_wx.service.cdy.WxAddressService;
import com.cskaoyan.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxAddressServiceImpl implements WxAddressService {

    @Autowired
    AddressMapper addressMapper;

    @Override
    public List<Address> queryByUid(Integer userId) {
        AddressExample addressExample = new AddressExample();
        addressExample.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return addressMapper.selectByExample(addressExample);
    }
}
