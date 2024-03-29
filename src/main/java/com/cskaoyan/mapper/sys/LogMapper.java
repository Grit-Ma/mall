package com.cskaoyan.mapper.sys;

import com.cskaoyan.bean.sys.Log;
import com.cskaoyan.bean.sys.LogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogMapper {

    List<Log> fuzzyQueryByName(@Param("name") String name,@Param("queryorder")String order);

    long countByExample(LogExample example);

    int deleteByExample(LogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    List<Log> selectByExample(LogExample example);

    Log selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Log record, @Param("example") LogExample example);

    int updateByExample(@Param("record") Log record, @Param("example") LogExample example);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}