package com.cskaoyan.mall_wx.controller.zhe;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.collect.TypeAndValueId;
import com.cskaoyan.bean.wx.pagedata.CollectPageData;
import com.cskaoyan.mall_wx.service.zhe.WxCollectService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("wx")
public class WxCollectController {
    @Autowired
    WxCollectService collectService;
    //wx/collect/list?type=0&page=1&size=10
    @RequestMapping("collect/list")
    @ResponseBody
    public ResponseVO collectList(int page, int size, Integer type, HttpServletRequest request){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
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

    //wx/collect/addordelete
    @RequestMapping("collect/addordelete")
    @ResponseBody
    public ResponseVO collectAddOrDelete(@RequestBody TypeAndValueId typeAndValueId,HttpServletRequest request){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        String result = collectService.collectAddOrDelete(typeAndValueId,userId);
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        if(result != null && result.equals("add")){
            HashMap<String, Object> map = new HashMap<>();
            map.put("type","add");
            responseVO.setData(map);
            return responseVO;
        }else if(result != null && result.equals("delete")){
            HashMap<String, Object> map = new HashMap<>();
            map.put("type","add");
            responseVO.setData(map);
            return responseVO;
        }
        return responseVO;
    }

}
