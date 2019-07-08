package com.cskaoyan.mall_admin.service.mallManageService;

import com.cskaoyan.bean.Keyword;
import com.cskaoyan.bean.vo.PageData;

public interface KeywordService {
    PageData keywordList(int page, int limit, String sort, String order, String keyword, String url);

    Keyword createKeyword(Keyword keyword);

    Keyword updateKeyword(Keyword keyword);

    int deleteKeyword(Keyword keyword);
}
