package com.cskaoyan.mall_admin.service.promotion;

import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;


/**
 * created by ZengWei
 * on 2019/7/3
 */
public interface AdService {
    PageData getAdListData(int page, int limit, String sort, String order);

    ResponseVO insertAd(Ad ad);

    ResponseVO updateAd(Ad ad);

    ResponseVO deleteAd(Ad ad);

    PageData searchAdByName(int page, int limit, String sort, String order, String name);

    PageData searchAdByContent(int page, int limit, String sort, String order, String content);

    PageData searchAdByNameAndContent(int page, int limit, String sort, String order, String name, String content);
}
