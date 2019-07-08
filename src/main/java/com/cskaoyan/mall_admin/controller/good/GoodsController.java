package com.cskaoyan.mall_admin.controller.good;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GoodsInfo;
import com.cskaoyan.bean.vo.CatAndBrandVo;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ProductVo;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.good.GoodService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.HashMap;


@Controller
public class GoodsController {
    @Autowired
    GoodService goodService;


    @RequestMapping("goods/list")
    @RequiresPermissions(value = "admin:goods:list")
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

    @RequestMapping("goods/detail")
    @RequiresPermissions("admin:goods:read")
    @ResponseBody
    public ResponseVO details(@RequestParam("id")int id){
        ResponseVO vo = new ResponseVO();
        ProductVo data = goodService.getGoodById(id);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("goods/delete")
    @RequiresPermissions("admin:goods:delete")
    @ResponseBody
    public ResponseVO delete(@RequestBody Goods goods){
        ResponseVO<Object> vo = new ResponseVO<>();
        goodService.delete(goods);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequestMapping("goods/update")
    @RequiresPermissions("admin:goods:update")
    @ResponseBody
    public ResponseVO update(@RequestBody GoodsInfo goodsInfo){
        ResponseVO<Object> vo = new ResponseVO<>();
        int errno = goodService.check(goodsInfo);
        String errmsg = "成功";
        if(errno == 401){
            errmsg = "参数不对";
        }else {
            goodService.update(goodsInfo);
        }
        vo.setErrmsg(errmsg);
        vo.setErrno(errno);
        return vo;
    }

    @RequestMapping("goods/create")
    @RequiresPermissions("admin:goods:create")
    @ResponseBody
    public ResponseVO create(@RequestBody GoodsInfo goodsInfo){
        ResponseVO<Object> vo = new ResponseVO<>();
        int errno = goodService.check(goodsInfo);
        String errmsg = "成功";
        if(errno == 401){
            errmsg = "参数不对";
        }else {
            goodService.create(goodsInfo);
        }
        vo.setErrmsg(errmsg);
        vo.setErrno(errno);
        return vo;
    }

    @RequestMapping("goods/catAndBrand")
    @ResponseBody
    public ResponseVO catAndBrand(){
        ResponseVO vo = new ResponseVO();
        CatAndBrandVo data = goodService.getCatAndBrand();
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("comment/list")
    @RequiresPermissions("admin:comment:list")
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

    @RequestMapping("comment/delete")
    @RequiresPermissions("admin:comment:delete")
    @ResponseBody
    public ResponseVO delete(@RequestBody Comment comment){
        ResponseVO vo = new ResponseVO();
        goodService.delete(comment);
        return vo;
    }

    @RequestMapping("order/reply")
    @RequiresPermissions("admin:order:reply")
    @ResponseBody
    public ResponseVO reply(@RequestBody HashMap<String,Object> map){
        ResponseVO vo = new ResponseVO();
        int commentId = (int) map.get("commentId");
        String content = ((String) map.get("content"));
        //缺少商品评论回复表
        vo.setErrmsg("订单商品已回复");
        vo.setErrno(622);
        return vo;
    }


}
