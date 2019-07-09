package com.cskaoyan.mall_wx.controller.zhe;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_wx.service.zhe.WxSearchService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("wx")
public class WxSearchController {
    @Autowired
    WxSearchService wxSearchService;
    //wx/search/index
    @RequestMapping("search/index")
    @ResponseBody
    public ResponseVO searchIndex(HttpServletRequest request){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
            HashMap<String,Object> map = wxSearchService.searchIndex(userId);
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(map);
            return responseVO;
    }

    //wx/search/clearhistory
    @RequestMapping("search/clearhistory")
    @ResponseBody
    public ResponseVO cleanSearchHistory(HttpServletRequest request){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        int i = wxSearchService.cleanSearchHistory(userId);
        if(i >= 1){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
        }
        return responseVO;
    }

    //wx/search/helper?keyword=%E4%BA%91%E7%AB%AF
    @RequestMapping("search/helper")
    @ResponseBody
    public ResponseVO searchHelper(String keyword){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        List<String> list = wxSearchService.searchHelper(keyword);
        responseVO.setData(list);
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        return responseVO;
    }

}
