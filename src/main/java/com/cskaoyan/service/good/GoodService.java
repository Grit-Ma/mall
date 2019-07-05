package com.cskaoyan.service.good;

import com.cskaoyan.bean.GoodsInfo;
import com.cskaoyan.bean.vo.CatAndBrandVo;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ProductVo;
import org.springframework.stereotype.Service;

@Service
public interface GoodService {
    PageData getListData(int page, int limit, String sort, String order,String goodsSn,String name);

    PageData getCommentListData(int page, int limit, String sort, String order, String userId, String valueId);

    ProductVo getGoodById(int id);


    CatAndBrandVo getCatAndBrand();

    int update(GoodsInfo map);
}
