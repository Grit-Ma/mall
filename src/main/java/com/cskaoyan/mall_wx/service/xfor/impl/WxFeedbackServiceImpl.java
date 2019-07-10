package com.cskaoyan.mall_wx.service.xfor.impl;

import com.cskaoyan.bean.Feedback;
import com.cskaoyan.bean.User;
import com.cskaoyan.bean.wx.xfor.WxFeedback;
import com.cskaoyan.mall_wx.service.xfor.WxFeedbackService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import com.cskaoyan.mapper.FeedbackMapper;
import com.cskaoyan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Service
public class WxFeedbackServiceImpl implements WxFeedbackService {
    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public int storeFeecback(HttpServletRequest request, WxFeedback wxFeedback) {
        String token = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(token);
        User user = userMapper.selectByPrimaryKey(userId);
        Feedback feedback = new Feedback();
        feedback.setUserId(user.getId());
        feedback.setUsername(user.getUsername());
        feedback.setStatus(user.getStatus().intValue());
        feedback.setContent(wxFeedback.getContent());
        feedback.setFeedType(wxFeedback.getFeedType());
        feedback.setHasPicture(wxFeedback.getHasPicture());
        feedback.setMobile(wxFeedback.getMobile());
        if (wxFeedback.getPicUrls().length != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String picUrl : wxFeedback.getPicUrls()) {
                stringBuffer.append(picUrl).append(",");
            }
            String urls = stringBuffer.substring(0, stringBuffer.length() - 1);
            feedback.setPicUrls(urls);
        }
        int insert = feedbackMapper.insert(feedback);
        return insert != 0 ? 1 : 0;
    }
}
