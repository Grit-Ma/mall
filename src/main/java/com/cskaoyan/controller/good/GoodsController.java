package com.cskaoyan.controller.good;

import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.good.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class GoodsController {
    @Autowired
    GoodService goodService;

    @RequestMapping("admin/goods/list")
    @ResponseBody
    public ResponseVO list(int page,int limit,String sort,String order){
        ResponseVO vo = new ResponseVO();
        PageData data = goodService.getListData(page, limit, sort, order);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("admin/comment/list")
    @ResponseBody
    public ResponseVO commentList(int page,int limit,String sort,String order){
        ResponseVO vo = new ResponseVO();
        PageData data = goodService.getCommentListData(page, limit, sort, order);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }
}
