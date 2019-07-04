package com.cskaoyan.controller.user;

import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.user.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("admin")
public class SearchHistoryController {

    @Autowired
    SearchHistoryService userService;

    //搜索历史List
    @RequestMapping("history/list")
    @ResponseBody
    public ResponseVO userSearchHistory(int page, int limit, String sort, String order){
        ResponseVO responseVO = new ResponseVO();
        PageData data = userService.searchHistoryList(page,limit,sort,order);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }
}
