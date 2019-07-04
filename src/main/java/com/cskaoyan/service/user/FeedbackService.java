package com.cskaoyan.service.user;

import com.cskaoyan.bean.vo.PageData;

public interface FeedbackService {

    //意见反馈list
    PageData feedback(int page, int limit, String sort, String order);

    //意见反馈查询
    PageData feedbackSearch(int page, int limit, String sort,
                            String order, String usename, Integer id);
}
