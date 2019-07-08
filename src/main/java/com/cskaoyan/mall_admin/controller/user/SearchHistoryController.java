package com.cskaoyan.mall_admin.controller.user;

import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.user.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("history")
public class SearchHistoryController {

    @Autowired
    SearchHistoryService userService;

    //搜索历史List+模糊查询
    @RequestMapping("list")
    @ResponseBody
    public ResponseVO userSearchHistory(int page, int limit, String sort,
                                        String order, Integer userId, String keyword){

        ResponseVO responseVO = new ResponseVO();
        if (userId==null && keyword==null) {
            PageData data = userService.searchHistoryList(page,limit,sort,order);
            responseVO.setErrmsg("成功");
            responseVO.setData(data);
        }else {
            PageData data = userService.searchHistorySearch(page, limit, sort, order, userId, keyword);
            responseVO.setErrmsg("成功");
            responseVO.setData(data);
        }
        return responseVO;
    }


}
