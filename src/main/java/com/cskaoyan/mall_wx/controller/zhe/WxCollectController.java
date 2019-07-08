package com.cskaoyan.mall_wx.controller.zhe;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.pagedata.CollectPageData;
import com.cskaoyan.mall_wx.service.zhe.WxCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("wx")
public class WxCollectController {
    @Autowired
    WxCollectService collectService;
    //wx/collect/list?type=0&page=1&size=10
    @RequestMapping("collect/list")
    @ResponseBody
    public ResponseVO collectList(int page, int size, Integer type){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        //获取userid
        Integer userId = 1;
        CollectPageData data = collectService.collectList(page,size,type,userId);
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

}
