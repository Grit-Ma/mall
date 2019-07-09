package com.cskaoyan.mall_wx.service.zhe;

import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.wx.pagedata.TopicPageData;

import java.util.List;

public interface WxTopicService {
    TopicPageData topicList(int page, int size);

    Topic topicDetail(Integer id);

    List<Topic> topicRelated(Integer id);
}
