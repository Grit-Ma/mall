package com.cskaoyan.service.good;

import com.cskaoyan.bean.vo.PageData;
import org.springframework.stereotype.Service;


@Service
public interface GoodService {
    PageData getListData(int page, int limit, String sort, String order);

    PageData getCommentListData(int page, int limit, String sort, String order);
}
