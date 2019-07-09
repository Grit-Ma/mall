package com.cskaoyan.mall_wx.service.login.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.CategoryExample;
import com.cskaoyan.bean.wx.xfor.CatalogList;
import com.cskaoyan.bean.wx.xfor.CurrentCatalogList;
import com.cskaoyan.mall_wx.service.login.CatalogService;
import com.cskaoyan.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    HomeServiceImpl homeService;

    @Override
    public CatalogList getCatalogList() {
        List<Category> categoryList = homeService.getChannel();
        Category currentCategory = categoryMapper.selectByPrimaryKey(categoryList.get(0).getId());
        List<Category> currentSubCategory = getCurrentSubCategory(categoryList.get(0).getId());
        CatalogList catalogList = new CatalogList(categoryList, currentCategory, currentSubCategory);
        return catalogList;
    }

    @Override
    public CurrentCatalogList getCurrentCatalogList(int id) {
        Category currentCategory = categoryMapper.selectByPrimaryKey(id);
        List<Category> currentSubCategory = getCurrentSubCategory(id);
        CurrentCatalogList currentCatalogList = new CurrentCatalogList(currentCategory, currentSubCategory);
        return currentCatalogList;
    }

    private List<Category> getCurrentSubCategory(int pid) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(pid);
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        return categoryList;
    }
}
