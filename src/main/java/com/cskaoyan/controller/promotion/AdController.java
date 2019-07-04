package com.cskaoyan.controller.promotion;


import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.promotion.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * created by ZengWei
 * on 2019/7/3
 */

@Controller
public class AdController {

    @Autowired
    AdService adService;

    @RequestMapping("admin/ad/list")
    @ResponseBody
    public ResponseVO list( int page, int limit, String sort, String order, String name, String content) {
        ResponseVO vo = new ResponseVO();
        if(name == null && content == null ){
            PageData data = adService.getAdListData(page, limit, sort, order);
            vo.setErrmsg("成功");
            vo.setData(data);
        }else if(content == null){
            PageData data = adService.searchAdByName(page, limit, sort, order, name);
            vo.setErrmsg("成功");
            vo.setData(data);
        }else if(name == null){
            PageData data = adService.searchAdByContent(page, limit, sort, order, content);
            vo.setErrmsg("成功");
            vo.setData(data);
        }else{
            PageData data = adService.searchAdByNameAndContent(page, limit, sort, order, name, content);
            vo.setErrmsg("成功");
            vo.setData(data);
        }
        return vo;
    }

    @PostMapping("admin/ad/update")
    @ResponseBody
    public ResponseVO update(@RequestBody Ad ad) {
        ResponseVO vo = new ResponseVO();
        vo = adService.updateAd(ad);
        return vo;
    }

    @PostMapping("admin/ad/delete")
    @ResponseBody
    public ResponseVO delete(@RequestBody Ad ad) {
        ResponseVO vo = new ResponseVO();
        vo = adService.deleteAd(ad);
        return vo;
    }
}
