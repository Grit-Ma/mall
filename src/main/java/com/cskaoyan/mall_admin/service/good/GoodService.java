package com.cskaoyan.mall_admin.service.good;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.Goods;
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

    void delete(Goods goods);

    void delete(Comment comment);

    void create(GoodsInfo goodsInfo);

    int check(GoodsInfo goodsInfo);
}
