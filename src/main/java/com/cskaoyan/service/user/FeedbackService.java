package com.cskaoyan.service.user;

import com.cskaoyan.bean.vo.PageData;

public interface FeedbackService {

    //意见反馈list
    PageData feedback(int page, int limit, String sort, String order);
}
