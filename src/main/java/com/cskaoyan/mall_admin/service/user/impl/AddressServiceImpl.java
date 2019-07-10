package com.cskaoyan.mall_admin.service.user.impl;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.AddressExample;
import com.cskaoyan.bean.AddressPackage;
import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.AddressMapper;
import com.cskaoyan.mall_admin.service.user.AddressService;
import com.cskaoyan.mapper.RegionMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    RegionMapper regionMapper;

    @Override
    public PageData getAddress(int page, int limit, String name, String userId, String sort, String order) {
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        if ("".equals(name) && "".equals(userId)) {
        } else if (!("".equals(name)) && !("".equals(userId))) {
            criteria.andNameLike("%" + name + "%");
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        } else if ("".equals(name)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        } else if ("".equals(userId)) {
            criteria.andNameLike("%" + name + "%");
        }
        long l = addressMapper.countByExample(addressExample);
        PageHelper.startPage(page, limit, sort + " " + order);
        List<Address> addressList = addressMapper.selectByExample(addressExample);
        List<AddressPackage> addressPackages = new ArrayList<>();
        for (Address address : addressList) {
            addressPackages.add(getAddressPackage(address));
        }
        return new PageData(addressPackages, l);
    }

    private AddressPackage getAddressPackage(Address address) {
        AddressPackage aPackage = new AddressPackage();
        aPackage.setAddress(address.getAddress());
        aPackage.setIsDefault(address.getIsDefault());

        Region r1 = regionMapper.selectByPrimaryKey(address.getProvinceId());
        if (r1 != null) {
            aPackage.setProvince(r1.getName());
        } else {
            aPackage.setProvince(null);
        }
        aPackage.setProvinceId(address.getProvinceId());
        aPackage.setCityId(address.getCityId());
        aPackage.setAreaId(address.getAreaId());
        aPackage.setUserId(address.getUserId());
        aPackage.setId(address.getId());

        Region r2 = regionMapper.selectByPrimaryKey(address.getCityId());
        if (r2 != null) {
            aPackage.setCity(r2.getName());
        } else {
            aPackage.setCity(null);
        }

        aPackage.setMobile(address.getMobile());
        aPackage.setName(address.getName());
        aPackage.setDeleted(address.getDeleted());

        Region r3 = regionMapper.selectByPrimaryKey(address.getAreaId());
        if (r3 != null) {
            aPackage.setArea(r3.getName());
        } else {
            aPackage.setArea(null);
        }
        return aPackage;
    }
}
