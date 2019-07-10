package com.cskaoyan.mapper;

import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GoodsExample;
import com.cskaoyan.bean.GoodsInfo;
import com.cskaoyan.bean.vo.GoodsListVo;
import com.cskaoyan.bean.vo.WxGoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    long countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    GoodsInfo getGoodsInfo(int goodsId);

    GoodsListVo getWxGoodsList(int categoryId);

    int getGoodsCount();

    List<WxGoodsVo> selectWxGoodsByCategoryId(int categoryId);

    List<WxGoodsVo>  selectLikeGoodsName(String goodsName, String orderByClause);

    List<WxGoodsVo> selectRelated(int id);

    List<WxGoodsVo> selectByPrimaryKeys(@Param("keys") int[] keys);

    GoodsListVo selectGoodsListByBrandId(Integer brandId);
}