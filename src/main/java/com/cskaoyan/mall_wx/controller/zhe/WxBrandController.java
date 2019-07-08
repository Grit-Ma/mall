package com.cskaoyan.mall_wx.controller.zhe;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.pagedata.BrandPageData;
import com.cskaoyan.mall_wx.service.zhe.WxBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("wx")
public class WxBrandController {
    @Autowired
    WxBrandService wxBrandService;
    //wx/brand/list?page=1&size=10
    @RequestMapping("brand/list")
    @ResponseBody
    public ResponseVO brandList(int page, int size){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        BrandPageData data = wxBrandService.brandList(page,size);
        if(data != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(data);
            return responseVO;
        }else {
            responseVO.setErrmsg("failure");
            return responseVO;
        }
    }
    //wx/brand/detail?id=1001002
    @RequestMapping("brand/detail")
    @ResponseBody
    public ResponseVO brandDetail(Integer id){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        Brand brand = wxBrandService.brandDetail(id);
        if(brand != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(brand);
            return responseVO;
        }
        responseVO.setErrmsg("failure");
        return responseVO;
    }

}
