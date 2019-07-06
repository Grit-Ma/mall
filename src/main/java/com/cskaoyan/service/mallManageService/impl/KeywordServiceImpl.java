package com.cskaoyan.service.mallManageService.impl;

import com.cskaoyan.bean.Keyword;
import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.KeywordMapper;
import com.cskaoyan.service.mallManageService.KeywordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService {
    @Autowired
    KeywordMapper keywordMapper;
    @Override
    public PageData keywordList(int page, int limit, String sort, String order,String keyword,String url) {
        PageHelper.startPage(page,limit);
        KeywordExample example = new KeywordExample();
        if(keyword!= null && keyword.trim().length() != 0){
            keyword = "%" + keyword.trim() + "%";
        example.createCriteria().andKeywordLike(keyword);
        }
        if(url != null && url.trim().length() != 0){
            url = "%" + url.trim() + "%";
            example.createCriteria().andUrlLike(url);
        }
        example.createCriteria().andDeletedEqualTo(false);
        example.setOrderByClause(sort + "  " + order);
        List<Keyword> list = keywordMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return new PageData(pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public Keyword createKeyword(Keyword keyword) {
        Date date = new Date();
        keyword.setAddTime(date);
        keyword.setUpdateTime(date);
        keyword.setSortOrder(100);
        int insert = keywordMapper.insert(keyword);
        return keyword;
    }

    @Override
    public Keyword updateKeyword(Keyword keyword) {
        Date date = new Date();
        keyword.setUpdateTime(date);
        int i = keywordMapper.updateByPrimaryKeySelective(keyword);
        if(i >= 1){
            return keyword;
        }
        return null;
    }

    @Override
    public int deleteKeyword(Keyword keyword) {
        Date date = new Date();
        keyword.setUpdateTime(date);
        keyword.setDeleted(true);
        int i = keywordMapper.updateByPrimaryKeySelective(keyword);
        return i;
    }
}
