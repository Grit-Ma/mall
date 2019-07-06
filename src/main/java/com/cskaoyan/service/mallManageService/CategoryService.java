package com.cskaoyan.service.mallManageService;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.mallmanage.category.CategoryL1;
import com.cskaoyan.bean.mallmanage.category.CategoryList;

import java.util.List;

public interface CategoryService {
    List<CategoryList> categoryList();

    List<CategoryL1> categoryL1();

    Category createCategory(Category category);

    int updateCategory(CategoryList category);

    int deleteCategory(CategoryList category);
}
