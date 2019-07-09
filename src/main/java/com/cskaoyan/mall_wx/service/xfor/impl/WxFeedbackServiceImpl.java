package com.cskaoyan.mall_wx.service.xfor.impl;

import com.cskaoyan.bean.Feedback;
import com.cskaoyan.bean.wx.xfor.WxFeedback;
import com.cskaoyan.mall_wx.service.xfor.WxFeedbackService;
import com.cskaoyan.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxFeedbackServiceImpl implements WxFeedbackService {
    @Autowired
    FeedbackMapper feedbackMapper;

    @Override
    public int storeFeecback(WxFeedback wxFeedback) {
        Feedback feedback = new Feedback();
        feedback.setUserId(1);
        feedback.setUsername("lanzhao");
        feedback.setStatus(0);
        feedback.setContent(wxFeedback.getContent());
        feedback.setFeedType(wxFeedback.getFeedType());
        feedback.setHasPicture(wxFeedback.getHasPicture());
        feedback.setMobile(wxFeedback.getMobile());
        StringBuffer stringBuffer = new StringBuffer();
        for (String picUrl : wxFeedback.getPicUrls()) {
            stringBuffer.append(picUrl).append(",");
        }
        String urls = stringBuffer.substring(0, stringBuffer.length() - 1);
        feedback.setPicUrls(urls);
        int insert = feedbackMapper.insert(feedback);
        return insert != 0 ? 1 : 0;
    }
}
