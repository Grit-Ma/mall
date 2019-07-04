package com.cskaoyan.service.user;

import com.cskaoyan.bean.vo.PageData;

public interface SearchHistoryService {

    //搜索历史list
    PageData searchHistoryList(int page, int limit, String sort, String order);

}
