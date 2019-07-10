package com.cskaoyan.mall_wx.controller.goods;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.vo.*;
import com.cskaoyan.mall_wx.service.goods.WxGoodsService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

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
    public ResponseVO list(Integer categoryId, Integer page, Integer size, String keyword,
                           String sort,
                           String order,
                           Integer brandId,
                           HttpServletRequest request){
        ResponseVO vo = new ResponseVO();
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        GoodsListVo data = wxGoodsService.getWxListData(categoryId,page,size,keyword,sort,order,userId,brandId);
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
    public ResponseVO detail(@RequestParam(name = "id") int id, HttpServletRequest request){
        ResponseVO vo = new ResponseVO();
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        GoodsDeatil data = wxGoodsService.getDetail(id,userId);
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;
    }


    @RequestMapping("goods/related")
    @ResponseBody
    public ResponseVO related(@RequestParam(name = "id") int id){
        ResponseVO vo = new ResponseVO();
        List<WxGoodsVo> data = wxGoodsService.getRelated(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("goodsList",data);
        vo.setData(map);
        vo.setErrmsg("成功");
        return vo;
    }


    @RequestMapping("comment/list")
    @ResponseBody
    public ResponseVO commentList(int valueId,
                                  int type,
                                  int size,
                                  int page,
                                  int showType){
        ResponseVO<Object> vo = new ResponseVO<>();
        HashMap map = wxGoodsService.commentList(valueId,type,size,page,showType);
        vo.setData(map);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequestMapping("comment/count")
    @ResponseBody
    public ResponseVO commentCount(@RequestParam(name = "valueId")int valueId, int type){
        ResponseVO<Object> vo = new ResponseVO<>();
        HashMap map = wxGoodsService.commentCount(valueId,type);
        vo.setData(map);
        vo.setErrmsg("成功");
        return vo;
    }

    @RequestMapping("comment/post")
    @ResponseBody
    public ResponseVO commentPost(@RequestBody Comment comment){
        ResponseVO<Object> vo = new ResponseVO<>();
        Comment ret = wxGoodsService.commentPost(comment);
        vo.setData(ret);
        vo.setErrmsg("成功");
        return vo;
    }
}
