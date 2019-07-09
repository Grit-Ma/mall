package com.cskaoyan.mall_wx.service.cdy.impl;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.AddressExample;
import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.wx.WxAddress;
import com.cskaoyan.mall_wx.service.cdy.WxAddressService;
import com.cskaoyan.mapper.AddressMapper;
import com.cskaoyan.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxAddressServiceImpl implements WxAddressService {

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Address> queryByUid(Integer userId) {
        AddressExample addressExample = new AddressExample();
        addressExample.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Address> addressList = addressMapper.selectByExample(addressExample);
        for (Address address : addressList) {
            WxAddress query = query(userId, address.getId());
            address.setDetailedAddress(
                    query.getProvinceName() + query.getCityName() + query.getAreaName()
                    + " " + query.getAddress());
        }
        return addressList;
    }

    @Override
    public WxAddress query(Integer userId, Integer id) {
        AddressExample addressExample = new AddressExample();
        addressExample.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        Address address = addressMapper.selectByExample(addressExample).get(0);

        return getWxAddress(address);
    }

    private WxAddress getWxAddress(Address address) {
        WxAddress wxAddress = new WxAddress();
        wxAddress.setAddress(address.getAddress());
        wxAddress.setIsDefault(address.getIsDefault());
        wxAddress.setProvinceId(address.getProvinceId());
        wxAddress.setCityId(address.getCityId());
        wxAddress.setAreaId(address.getAreaId());
        wxAddress.setId(address.getId());
        wxAddress.setMobile(address.getMobile());
        wxAddress.setName(address.getName());
        wxAddress.setDeleted(address.getDeleted());

        Region r1 = regionMapper.selectByPrimaryKey(address.getProvinceId());
        wxAddress.setProvinceName(r1.getName());
        Region r2 = regionMapper.selectByPrimaryKey(address.getCityId());
        wxAddress.setCityName(r2.getName());
        Region r3 = regionMapper.selectByPrimaryKey(address.getAreaId());
        wxAddress.setAreaName(r3.getName());
        return wxAddress;
    }

    @Override
    public Integer update(Address address) {
        Address address1 = addressMapper.selectByPrimaryKey(address.getId());
        int i = 0;
        if (null == address1) {
            i = addressMapper.insert(address);
        } else {
            i = addressMapper.updateByPrimaryKey(address);
        }
        return i;
    }

    @Override
    public Integer delete(Integer id) {
        Address address = addressMapper.selectByPrimaryKey(id);
        address.setDeleted(true);
        int i = addressMapper.updateByPrimaryKey(address);
        return i;
    }
}
