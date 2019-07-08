package com.cskaoyan.mapper;

import com.cskaoyan.bean.Collect;
import com.cskaoyan.bean.CollectExample;
import com.cskaoyan.bean.wx.collect.WxCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectMapper {
    long countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    List<WxCollect> selectCollectByTypeAndUserId(@Param("type") Integer type,@Param("userId") Integer userId);
}