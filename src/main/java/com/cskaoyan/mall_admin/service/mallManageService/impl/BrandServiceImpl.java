package com.cskaoyan.mall_admin.service.mallManageService.impl;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.BrandExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.BrandMapper;
import com.cskaoyan.mall_admin.service.mallManageService.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandMapper brandMapper;

    @Override
    public PageData brandList(int page, int limit, String sort, String order,Integer id,String name) {
        PageHelper.startPage(page,limit);
        BrandExample brandExample = new BrandExample();
        if(id != null){
            brandExample.createCriteria().andIdEqualTo(id);
        }
        if(name != null && name.trim().length() !=0){
            name = "%" + name.trim() + "%";
            brandExample.createCriteria().andNameLike(name);
        }
        brandExample.createCriteria().andDeletedEqualTo(false);
        brandExample.setOrderByClause(sort + "  " + order);
        List<Brand> list = brandMapper.selectByExample(brandExample);
        PageInfo pageInfo = new PageInfo(list);
        return new PageData(pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public Brand createBrand(Brand brand) {
        Date date = new Date();
        brand.setAddTime(date);
        brand.setUpdateTime(date);
        int i = brandMapper.createBrand(brand);
        Integer id = brand.getId();
        Brand result = brandMapper.selectByPrimaryKey(id);
        return result;
    }

    @Override
    public Brand updateBrand(Brand brand) {
        Date date = new Date();
        brand.setUpdateTime(date);
        int i = brandMapper.updateByPrimaryKeySelective(brand);
        return brand;
    }

    @Override
    public int deleteBrand(Brand brand) {
        Date date = new Date();
        brand.setUpdateTime(date);
        brand.setDeleted(true);
        int i = brandMapper.updateByPrimaryKeySelective(brand);
        return i;
    }
}
