package com.cskaoyan.mall_admin.service.user;

import com.cskaoyan.bean.vo.PageData;

public interface CollectService {
    PageData getCollect(int page, int limit, String userId, String valueId, String sort, String order);
}
