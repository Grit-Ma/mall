package com.cskaoyan.mall_wx.controller.xfor;

import com.cskaoyan.bean.Feedback;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.xfor.WxFeedback;
import com.cskaoyan.mall_wx.service.xfor.WxFeedbackService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("wx/feedback")
public class WxFeedbackController {
    @Autowired
    WxFeedbackService wxFeedbackService;

    @PostMapping("submit")
    @ResponseBody
    public ResponseVO submitFeedback(HttpServletRequest request, @RequestBody WxFeedback wxFeedback) {
        int i = wxFeedbackService.storeFeecback(request, wxFeedback);
        if (i == 0) {
            return new ResponseVO(-1, null, "失败");
        }
        return new ResponseVO(0, null, "成功");
    }
}
