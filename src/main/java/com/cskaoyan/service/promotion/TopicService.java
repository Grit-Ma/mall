package com.cskaoyan.service.promotion;

import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;

/**
 * created by ZengWei
 * on 2019/7/5
 */
public interface TopicService {
    PageData getTopicListData(int page, int limit, String sort, String order, String title, String subtitle);

    ResponseVO updateTopic(Topic topic);

    ResponseVO deleteTopic(Topic topic);

    ResponseVO insertTopic(Topic topic);
}
