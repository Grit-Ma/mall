package com.cskaoyan.mall_admin.service.promotion;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GrouponRules;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;

/**
 * created by ZengWei
 * on 2019/7/6
 */
public interface GroupService {

    PageData getGroupListData(int page, int limit, String sort, String order, Integer goodsId);

    ResponseVO updateGroup(GrouponRules grouponRules);

    ResponseVO deleteGroup(GrouponRules grouponRules);

    ResponseVO insertGroup(GrouponRules grouponRules);

    Goods getGoodsById(int id);

    PageData getListRecordData(int page, int limit, String sort, String order);

    GrouponRules searchRulesById(int id);
}
