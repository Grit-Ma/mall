package com.cskaoyan.mall_admin.controller.mallManage;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.mallmanage.category.CategoryL1;
import com.cskaoyan.bean.mallmanage.category.CategoryList;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.mallManageService.CategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    //admin/category/list
    @RequestMapping("category/list")
    @ResponseBody
    @RequiresPermissions(value = "admin:category:list")
    public ResponseVO categoryList(){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        List<CategoryList> data = categoryService.categoryList();
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }
    //admin/category/l1
    @RequestMapping("category/l1")
    @ResponseBody
    @RequiresPermissions(value = "admin:category:list")
    public ResponseVO categoryL1(){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        List<CategoryL1> data = categoryService.categoryL1();
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }

    //admin/category/create
    @RequestMapping("category/create")
    @ResponseBody
    @RequiresPermissions(value = "admin:category:create")
    public ResponseVO createCategory(@RequestBody Category category){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        Category crate = categoryService.createCategory(category);
        if(crate.getId() != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(crate);
            return responseVO;
        }else {
            responseVO.setErrno(401);
            responseVO.setErrmsg("参数不对");
            return responseVO;
        }
    }

    //admin/category/update
    @RequestMapping("category/update")
    @ResponseBody
    @RequiresPermissions(value = "admin:category:update")
    public ResponseVO updateCategory(@RequestBody CategoryList category){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        int result = categoryService.updateCategory(category);
        if(result >= 1){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            return responseVO;
        }else {
            responseVO.setErrno(401);
            responseVO.setErrmsg("参数不对");
            return responseVO;
        }
    }

    //admin/category/delete
    @RequestMapping("category/delete")
    @ResponseBody
    @RequiresPermissions(value = "admin:category:delete")
    public ResponseVO deleteCategory(@RequestBody CategoryList category){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        int result = categoryService.deleteCategory(category);
        if(result >= 1){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            return responseVO;
        }else {
            responseVO.setErrno(401);
            responseVO.setErrmsg("参数不对");
            return responseVO;
        }
    }


}
