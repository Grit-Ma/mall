package com.cskaoyan.controller.good;

import com.cskaoyan.bean.GoodsInfo;
import com.cskaoyan.bean.vo.CatAndBrandVo;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ProductVo;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.good.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;


@Controller
public class GoodsController {
    @Autowired
    GoodService goodService;

    @RequestMapping("admin/goods/list")
    @ResponseBody
    public ResponseVO list(int page,int limit,String sort,String order,
                           @RequestParam(value="goodsSn",defaultValue="")String goodsSn,
                           @RequestParam(value="name",defaultValue="")String name){
        ResponseVO vo = new ResponseVO();
        PageData data = goodService.getListData(page, limit, sort, order,goodsSn,name);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("admin/goods/detail")
    @ResponseBody
    public ResponseVO details(@RequestParam("id")int id){
        ResponseVO vo = new ResponseVO();
        ProductVo data = goodService.getGoodById(id);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("admin/goods/update")
    @ResponseBody
    public ResponseVO update(@RequestBody GoodsInfo goodsInfo){
        ResponseVO<Object> vo = new ResponseVO<>();
        goodService.update(goodsInfo);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequestMapping("admin/goods/catAndBrand")
    @ResponseBody
    public ResponseVO catAndBrand(){
        ResponseVO vo = new ResponseVO();
        CatAndBrandVo data = goodService.getCatAndBrand();
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("admin/comment/list")
    @ResponseBody
    public ResponseVO commentList(int page,int limit,String sort,String order,String userId,String valueId){
        ResponseVO vo = new ResponseVO();
        try {
            PageData data = goodService.getCommentListData(page, limit, sort, order, userId, valueId);
            vo.setErrmsg("成功");
            vo.setData(data);
        }catch (InvalidParameterException e){
            vo.setErrno(402);
            vo.setErrmsg("参数值不对");
        }
        return vo;
    }


}
