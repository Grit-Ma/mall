package com.cskaoyan.service.user.impl;

import com.cskaoyan.bean.SearchHistory;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.SearchHistoryMapper;
import com.cskaoyan.service.user.SearchHistoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    @Autowired
    SearchHistoryMapper searchHistoryMapper;


    //搜索历史
    @Override
    public PageData searchHistoryList(int page, int limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        List<SearchHistory> searchHistoryList = searchHistoryMapper.selectAll();
        PageInfo pageInfo = new PageInfo(searchHistoryList);

        return new PageData(pageInfo.getList(),pageInfo.getTotal());
    }
}
