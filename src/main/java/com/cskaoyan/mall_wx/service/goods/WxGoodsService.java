package com.cskaoyan.mall_wx.service.goods;

import com.cskaoyan.bean.vo.CategoryVo;
import com.cskaoyan.bean.vo.GoodsDeatil;
import com.cskaoyan.bean.vo.GoodsListVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;

public interface WxGoodsService {

    GoodsListVo getWxListData(int categoryId, int page, int size);

    CategoryVo getCategory(int id);

    GoodsDeatil getDetail(int id);

    HashMap getCount();
}
