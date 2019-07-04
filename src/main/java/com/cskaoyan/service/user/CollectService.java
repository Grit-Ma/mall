package com.cskaoyan.service.user;

import com.cskaoyan.bean.vo.ResponseVO2;

public interface CollectService {
    ResponseVO2 getCollect(int page, int limit, String userId, String valueId, String sort, String order);
}
