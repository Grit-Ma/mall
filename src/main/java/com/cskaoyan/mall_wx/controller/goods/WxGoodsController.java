package com.cskaoyan.mall_wx.controller.goods;

import com.cskaoyan.bean.vo.CategoryVo;
import com.cskaoyan.bean.vo.GoodsDeatil;
import com.cskaoyan.bean.vo.GoodsListVo;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_wx.service.goods.WxGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@RequestMapping("wx")
@Controller
public class WxGoodsController {

    @Autowired
    WxGoodsService wxGoodsService;

    @RequestMapping("goods/count")
    @ResponseBody
    public ResponseVO count(){
        ResponseVO vo = new ResponseVO();
        HashMap data = wxGoodsService.getCount();
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequestMapping("goods/list")
    @ResponseBody
    public ResponseVO list(int categoryId, int page, int size,String keyword,
                           String sort,
                           String order){
        ResponseVO vo = new ResponseVO();
        GoodsListVo data = wxGoodsService.getWxListData(categoryId,page,size,keyword,sort,order);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("goods/category")
    @ResponseBody
    public ResponseVO category(int id){
        ResponseVO vo = new ResponseVO();
        CategoryVo data = wxGoodsService.getCategory(id);
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequestMapping("goods/detail")
    @ResponseBody
    public ResponseVO detail(@RequestParam(name = "id") int id){
        ResponseVO vo = new ResponseVO();
        GoodsDeatil data = wxGoodsService.getDetail(id);
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;
    }


    @RequestMapping("goods/related")
    @ResponseBody
    public ResponseVO related(@RequestParam(name = "id") int id){
        ResponseVO vo = new ResponseVO();
        //GoodsDeatil data = wxGoodsService.getDetail(id);
        //vo.setData(data);

        //vo.setErrmsg("成功");
        return vo;
    }


    @RequestMapping("comment/list")
    @ResponseBody
    public ResponseVO commentList(@RequestParam(name = "valueId")int valueId,@RequestParam(name = "type")int type,int size,int page,int showType){
        ResponseVO<Object> vo = new ResponseVO<>();
        HashMap map = wxGoodsService.commentList(valueId,type,size,page,showType);
        vo.setData(map);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequestMapping("comment/count")
    @ResponseBody
    public ResponseVO commentCount(@RequestParam(name = "valueId")int valueId,int type){
        ResponseVO<Object> vo = new ResponseVO<>();
        HashMap map = wxGoodsService.commentCount(valueId,type);
        vo.setData(map);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequestMapping("comment/post")
    @ResponseBody
    public ResponseVO commentPost(){
        return null;
    }
}
