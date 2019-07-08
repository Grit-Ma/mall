package com.cskaoyan.mall_wx.service.zhe.impl;

import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.TopicExample;
import com.cskaoyan.bean.wx.pagedata.TopicPageData;
import com.cskaoyan.mall_wx.service.zhe.WxTopicService;
import com.cskaoyan.mapper.TopicMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxTopicServiceImpl implements WxTopicService {
    @Autowired
    TopicMapper topicMapper;
    @Override
    public TopicPageData topicList(int page, int size) {
        PageHelper.startPage(page,size);
        TopicExample example = new TopicExample();
        example.createCriteria().andDeletedEqualTo(false);
        List<Topic> topics = topicMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(topics);
        return new TopicPageData(pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public Topic topicDetail(Integer id) {
        Topic topic = new Topic();
        if(id != null){
            topic = topicMapper.selectByPrimaryKey(id);
        }
        return topic;
    }
}
