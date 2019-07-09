package com.cskaoyan.mapper;

import com.cskaoyan.bean.SearchHistory;
import com.cskaoyan.bean.SearchHistoryExample;
import com.cskaoyan.bean.wx.keyword.WxKeyword;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchHistoryMapper {
    long countByExample(SearchHistoryExample example);

    int deleteByExample(SearchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SearchHistory record);

    int insertSelective(SearchHistory record);

    List<SearchHistory> selectByExample(SearchHistoryExample example);

    SearchHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByExample(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByPrimaryKeySelective(SearchHistory record);

    int updateByPrimaryKey(SearchHistory record);


    List<SearchHistory> selectAll();

    List<SearchHistory> selectByUserIdKeyword(@Param("userId") int userid, @Param("keyword") String keyword);

    List<WxKeyword> selectKeywordHistory(@Param("userId") Integer userId);
}