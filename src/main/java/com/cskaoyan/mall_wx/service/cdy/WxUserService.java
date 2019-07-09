package com.cskaoyan.mall_wx.service.cdy;

import com.cskaoyan.bean.User;

import java.util.List;

public interface WxUserService {
    List<User> queryByUsername(String username);

    int updateById(User user);
}
