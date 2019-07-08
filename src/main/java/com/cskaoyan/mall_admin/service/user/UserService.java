package com.cskaoyan.mall_admin.service.user;

import com.cskaoyan.bean.vo.PageData;

public interface UserService {
    PageData getUser(int page, int limit, String username, String mobile, String sort, String order);
}
