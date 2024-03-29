package com.cskaoyan.mapper.sys;

import com.cskaoyan.bean.sys.Storage;
import com.cskaoyan.bean.sys.StorageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StorageMapper {
    long countByExample(StorageExample example);

    int deleteByExample(StorageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Storage record);

    int insertSelective(Storage record);

    List<Storage> selectByExample(StorageExample example);

    Storage selectByPrimaryKey(Integer id);

    Storage selectByKey(@Param("key") String key);

    int updateByExampleSelective(@Param("record") Storage record, @Param("example") StorageExample example);

    int updateByExample(@Param("record") Storage record, @Param("example") StorageExample example);

    int updateByPrimaryKeySelective(Storage record);

    int updateByPrimaryKey(Storage record);

    List<Storage> fuzzyQuery(@Param("key") String key, @Param("name") String name, @Param("sort") String sort, @Param("order") String order);
}