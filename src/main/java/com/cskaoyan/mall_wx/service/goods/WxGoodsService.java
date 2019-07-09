package com.cskaoyan.mall_wx.service.goods;

import com.cskaoyan.bean.vo.CategoryVo;
import com.cskaoyan.bean.vo.GoodsDeatil;
import com.cskaoyan.bean.vo.GoodsListVo;
import com.cskaoyan.bean.vo.WxGoodsVo;

import java.util.HashMap;
import java.util.List;

public interface WxGoodsService {

    GoodsListVo getWxListData(Integer categoryId, Integer page, Integer size, String keyword, String sort, String order, Integer userId, Integer brandId);

    CategoryVo getCategory(int id);

    GoodsDeatil getDetail(int id, Integer userId);

    HashMap getCount();

    HashMap commentCount(int valueId, int type);

    HashMap commentList(int valueId, int type, int size, int page, int showType);

    List<WxGoodsVo> getRelated(int id);

}
