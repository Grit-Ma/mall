package com.cskaoyan.mall_wx.service.xfor;

import com.cskaoyan.bean.wx.xfor.WxFeedback;

import javax.servlet.http.HttpServletRequest;

public interface WxFeedbackService {
    int storeFeecback(HttpServletRequest request, WxFeedback wxFeedback);
}
