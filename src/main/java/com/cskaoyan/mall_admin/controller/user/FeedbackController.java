package com.cskaoyan.mall_admin.controller.user;

import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.user.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    FeedbackService userService;

    //意见反馈List
    @RequestMapping("list")
    @ResponseBody
    public ResponseVO feedback(int page, int limit, String sort,
                               String order, String usename, Integer id){
        ResponseVO responseVO = new ResponseVO();
        if (usename==null && id==null) {
            PageData data = userService.feedback(page,limit,sort,order);
            responseVO.setErrmsg("成功");
            responseVO.setData(data);
        }else {
            PageData data = userService.feedbackSearch(page, limit, sort, order, usename, id);
            responseVO.setErrmsg("成功");
            responseVO.setData(data);
        }
        return responseVO;
    }
}
