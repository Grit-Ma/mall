package com.cskaoyan.service.user.impl;

import com.cskaoyan.bean.Feedback;
import com.cskaoyan.bean.FeedbackExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.FeedbackMapper;
import com.cskaoyan.service.user.FeedbackService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackMapper feedbackMapper;


    //意见反馈list
    @Override
    public PageData feedback(int page, int limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        List<Feedback> feedbacks = feedbackMapper.selectAll();
        PageInfo pageInfo = new PageInfo(feedbacks);

        return new PageData(pageInfo.getList(),pageInfo.getTotal());
    }

    //意见反馈查询
    @Override
    public PageData feedbackSearch(int page, int limit, String sort, String order, String usename, Integer id) {
        PageHelper.startPage(page,limit);
        FeedbackExample example = new FeedbackExample();
        FeedbackExample.Criteria criteria = example.createCriteria();
        if (usename!= null){
            criteria.andUsernameLike(usename);
        }
        if (id != null){
            criteria.andIdEqualTo(id);
        }
        List<Feedback> feedbackList = feedbackMapper.selectByExample(example);
        PageInfo pageinfo = new PageInfo(feedbackList);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

}
