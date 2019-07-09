package com.cskaoyan.mall_wx.service.zhe;

import java.util.HashMap;
import java.util.List;

public interface WxSearchService {
    HashMap<String, Object> searchIndex(Integer userId);

    int cleanSearchHistory(Integer userId);

    List<String> searchHelper(String keyword);
}
