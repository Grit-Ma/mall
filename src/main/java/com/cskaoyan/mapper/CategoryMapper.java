package com.cskaoyan.mapper;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.CategoryExample;
import com.cskaoyan.bean.mallmanage.category.CategoryL1;
import com.cskaoyan.bean.mallmanage.category.CategoryList;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CategoryMapper {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<CategoryList> selectCategoryList();

    List<CategoryL1> selectCategoryL1();

    int createCategory(@Param("category") Category category);

    HashMap getCategoryIds(Integer categoryId);

    List<Category> selectLikeGoodsName(String goodsName);

    List<Category> selectFilterCategoryList(int categoryId);
}