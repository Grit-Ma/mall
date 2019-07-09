package com.cskaoyan.mall_wx.service.zhe.impl;

import com.cskaoyan.bean.Keyword;
import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.bean.SearchHistory;
import com.cskaoyan.bean.SearchHistoryExample;
import com.cskaoyan.bean.wx.keyword.WxKeyword;
import com.cskaoyan.mall_wx.service.zhe.WxSearchService;
import com.cskaoyan.mapper.KeywordMapper;
import com.cskaoyan.mapper.SearchHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WxSearchServiceImpl implements WxSearchService {
    @Autowired
    KeywordMapper keywordMapper;
    @Autowired
    SearchHistoryMapper searchHistoryMapper;
    @Override
    public HashMap<String, Object> searchIndex(Integer userId) {
        HashMap<String, Object> map = new HashMap<>();
        KeywordExample example = new KeywordExample();
        example.createCriteria().andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        List<Keyword> defaultKeyword = keywordMapper.selectByExample(example);
        map.put("defaultKeyword",defaultKeyword.get(0));
        KeywordExample example2 = new KeywordExample();
        example2.createCriteria().andIsHotEqualTo(true).andDeletedEqualTo(false);
        List<Keyword> hotKeywordList = keywordMapper.selectByExample(example2);
        map.put("hotKeywordList",hotKeywordList);
        List<WxKeyword> historyKeywordList = new ArrayList<>();
        if(userId != null){
            historyKeywordList = searchHistoryMapper.selectKeywordHistory(userId);
        }
        map.put("historyKeywordList",historyKeywordList);
        return map;
    }

    @Override
    public int cleanSearchHistory(Integer userId) {
        Date date = new Date();
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setDeleted(true);
        searchHistory.setUpdateTime(date);
        SearchHistoryExample example = new SearchHistoryExample();
        example.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        int i = searchHistoryMapper.updateByExampleSelective(searchHistory, example);
        return i;
    }

    @Override
    public List<String> searchHelper(String keyword) {
        List<String> searchKeyword = new ArrayList<>();
        KeywordExample example = new KeywordExample();
        keyword = "%" + keyword + "%";
        example.createCriteria().andKeywordLike(keyword);
        List<Keyword> keywords = keywordMapper.selectByExample(example);
        if(keywords != null){
            for (Keyword k : keywords) {
                searchKeyword.add(k.getKeyword());
            }
        }
        return searchKeyword;
    }
}
