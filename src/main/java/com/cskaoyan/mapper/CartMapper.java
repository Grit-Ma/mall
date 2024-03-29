package com.cskaoyan.mapper;

import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.CartExample;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    long countByExample(CartExample example);

    int deleteByExample(CartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> selectByExample(CartExample example);

    Cart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Integer sumAll(@Param("userId")int userId);

    void deleteChecked(@Param("userId")int userId, @Param("list")List productsId);

    BigDecimal sumAllChecked(@Param("userId")int userId);

    int countAllCart(@Param("userId")int userId);
}