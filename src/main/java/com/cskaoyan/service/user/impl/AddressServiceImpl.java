package com.cskaoyan.service.user.impl;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.AddressExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.AddressMapper;
import com.cskaoyan.service.user.AddressService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;

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
        return new PageData(addressList, l);
    }
}
