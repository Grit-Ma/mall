package com.cskaoyan.mall_admin.service.user;

import com.cskaoyan.bean.vo.PageData;

public interface FootprintService {

    //会员足迹(footprint)list
    PageData footprintList(int page, int limit, String sort, String order);

    //会员足迹search
    PageData footprintSearch(int page, int limit, String sort, String order, Integer userId, Integer goodsId);
}
