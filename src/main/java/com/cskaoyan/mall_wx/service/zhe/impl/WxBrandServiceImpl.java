package com.cskaoyan.mall_wx.service.zhe.impl;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.BrandExample;
import com.cskaoyan.bean.wx.pagedata.BrandPageData;
import com.cskaoyan.mall_wx.service.zhe.WxBrandService;
import com.cskaoyan.mapper.BrandMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxBrandServiceImpl implements WxBrandService {
    @Autowired
    BrandMapper brandMapper;
    @Override
    public BrandPageData brandList(int page, int size) {
        PageHelper.startPage(page,size);
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andDeletedEqualTo(false);
        List<Brand> list = brandMapper.selectByExample(brandExample);
        PageInfo pageInfo = new PageInfo(list);
        return new BrandPageData(pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public Brand brandDetail(Integer id) {
        Brand brand = new Brand();
        if(id != null){
            brand = brandMapper.selectByPrimaryKey(id);
        }
        return brand;
    }
}
