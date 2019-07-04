package com.cskaoyan.service.user;

import com.cskaoyan.bean.vo.ResponseVO2;

public interface UserService {
    ResponseVO2 getUser(int page, int limit, String username, String mobile, String sort, String order);

}
