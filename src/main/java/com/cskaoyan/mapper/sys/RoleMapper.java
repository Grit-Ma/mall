package com.cskaoyan.mapper.sys;

import com.cskaoyan.bean.sys.Label;
import com.cskaoyan.bean.sys.Log;
import com.cskaoyan.bean.sys.Role;
import com.cskaoyan.bean.sys.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {

    List<Log> fuzzyQueryByName(@Param("name") String name,@Param("sort") String sort, @Param("order") String order);

    List<Label> selectAllRoleOptions();

    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<String> selectPermissions(@Param("id") int roleId);
}