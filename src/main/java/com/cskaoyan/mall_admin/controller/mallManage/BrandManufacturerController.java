package com.cskaoyan.mall_admin.controller.mallManage;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.mallManageService.BrandService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin")
public class BrandManufacturerController {
    @Autowired
    BrandService brandService;
    //admin/brand/list?page=1&limit=20&sort=add_time&order=desc
    //admin/brand/list?page=1&limit=20&id=1046023&name=&sort=add_time&order=desc
    //admin/brand/list?page=1&limit=20&id=&name=a&sort=add_time&order=desc
    @RequestMapping("brand/list")
    @ResponseBody
    @RequiresPermissions(value = "admin:brand:list")
    public ResponseVO brandList(int page,int limit,String sort,String order,Integer id,String name){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        PageData data = brandService.brandList(page,limit,sort,order,id,name);
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }

    //admin/brand/create
    @RequestMapping("brand/create")
    @ResponseBody
    @RequiresPermissions(value = "admin:brand:create")
    public ResponseVO  brandCreate(@RequestBody Brand brand){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        //brand.setPicUrl("123");
        Brand data = brandService.createBrand(brand);
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }

    //admin/brand/update
    @RequestMapping("brand/update")
    @ResponseBody
    @RequiresPermissions(value = "admin:brand:update")
    public ResponseVO  brandUpdate(@RequestBody Brand brand){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        Brand data = brandService.updateBrand(brand);
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }

    //admin/brand/delete
    @RequestMapping("brand/delete")
    @ResponseBody
    @RequiresPermissions(value = "admin:brand:delete")
    public ResponseVO deleteBrand(@RequestBody Brand brand){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        int i = brandService.deleteBrand(brand);
        if(i >= 1){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            return responseVO;
        }
        responseVO.setErrmsg("error");
        return responseVO;
    }

}
