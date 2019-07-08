package com.cskaoyan.mall_admin.service.mallManageService.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.mallmanage.category.CategoryL1;
import com.cskaoyan.bean.mallmanage.category.CategoryList;
import com.cskaoyan.mapper.CategoryMapper;
import com.cskaoyan.mall_admin.service.mallManageService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public List<CategoryList> categoryList() {
        List<CategoryList> lists = categoryMapper.selectCategoryList();
        return lists;
    }

    @Override
    public List<CategoryL1> categoryL1() {
        List<CategoryL1> categoryL1List = categoryMapper.selectCategoryL1();
        return categoryL1List;
    }

    @Override
    public Category createCategory(Category category) {
        Date date = new Date();
        category.setAddTime(date);
        category.setUpdateTime(date);
        int i = categoryMapper.createCategory(category);
        Integer id = category.getId();
        Category result = categoryMapper.selectByPrimaryKey(id);
        return result;
    }

    @Override
    public int updateCategory(CategoryList category) {
        int result = 0;
        Date date = new Date();
        Category category2 = new Category();
        category2.setDesc(category.getDesc());
        category2.setIconUrl(category.getIconUrl());
        category2.setId(category.getId());
        category2.setKeywords(category.getKeywords());
        category2.setLevel(category.getLevel());
        category2.setName(category.getName());
        category2.setPicUrl(category.getPicUrl());
        if(category.getPid() != null){
            category2.setPid(category.getPid());
        }
        category2.setUpdateTime(date);
        result = categoryMapper.updateByPrimaryKeySelective(category2);
        if (category.getChildren() != null && category.getPid() != null){
            List<Category> children = category.getChildren();
            Integer pid = category.getPid();
            for (Category child : children) {
                child.setPid(pid);
                child.setUpdateTime(date);
                result = categoryMapper.updateByPrimaryKeySelective(child);
            }
            return result;
        }
        return result;

    }

    @Override
    public int deleteCategory(CategoryList category) {
        int result = 0;
        Date date = new Date();
        Category category2 = new Category();
        category2.setDesc(category.getDesc());
        category2.setIconUrl(category.getIconUrl());
        category2.setId(category.getId());
        category2.setKeywords(category.getKeywords());
        category2.setLevel(category.getLevel());
        category2.setName(category.getName());
        category2.setPicUrl(category.getPicUrl());
        category2.setUpdateTime(date);
        category2.setDeleted(true);
        result = categoryMapper.updateByPrimaryKeySelective(category2);
        if (category.getChildren() != null){
            List<Category> children = category.getChildren();
            for (Category child : children) {
                child.setDeleted(true);
                child.setUpdateTime(date);
                result = categoryMapper.updateByPrimaryKeySelective(child);
            }
            return result;
        }
        return result;
    }
}
