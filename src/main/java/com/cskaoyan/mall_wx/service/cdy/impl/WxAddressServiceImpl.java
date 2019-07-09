package com.cskaoyan.mall_wx.service.cdy.impl;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.AddressExample;
import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.AddressPackage;
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
            AddressPackage query = query(userId, address.getId());
            address.setDetailedAddress(
                    query.getProvinceName() + query.getCityName() + query.getAreaName()
                    + " " + query.getAddress());
        }
        return addressList;
    }

    @Override
    public AddressPackage query(Integer userId, Integer id) {
        AddressExample addressExample = new AddressExample();
        addressExample.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        Address address = addressMapper.selectByExample(addressExample).get(0);

        return getAddressPackage(address);
    }

    private AddressPackage getAddressPackage(Address address) {
        AddressPackage addressPackage = new AddressPackage();
        addressPackage.setAddress(address.getAddress());
        addressPackage.setIsDefault(address.getIsDefault());
        addressPackage.setProvinceId(address.getProvinceId());
        addressPackage.setCityId(address.getCityId());
        addressPackage.setAreaId(address.getAreaId());
        addressPackage.setId(address.getId());
        addressPackage.setMobile(address.getMobile());
        addressPackage.setName(address.getName());
        addressPackage.setDeleted(address.getDeleted());

        Region r1 = regionMapper.selectByPrimaryKey(address.getProvinceId());
        addressPackage.setProvinceName(r1.getName());
        Region r2 = regionMapper.selectByPrimaryKey(address.getCityId());
        addressPackage.setCityName(r2.getName());
        Region r3 = regionMapper.selectByPrimaryKey(address.getAreaId());
        addressPackage.setAreaName(r3.getName());
        return addressPackage;
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
