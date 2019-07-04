package com.cskaoyan.controller.user;

import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.user.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("admin")
public class FeedbackController {

    @Autowired
    FeedbackService userService;

    //意见反馈List
    @RequestMapping("feedback/list")
    @ResponseBody
    public ResponseVO feedback(int page, int limit, String sort, String order){
        ResponseVO responseVO = new ResponseVO();
        PageData data = userService.feedback(page,limit,sort,order);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }
}
