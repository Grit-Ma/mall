package com.cskaoyan.mall_wx.service.cdy.impl;

import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GrouponRules;
import com.cskaoyan.bean.GrouponRulesExample;
import com.cskaoyan.bean.wx.WxGroupon;
import com.cskaoyan.bean.wx.WxListVo;
import com.cskaoyan.mall_wx.service.cdy.WxGrouponService;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.GrouponRulesMapper;
import com.cskaoyan.tool.WrapTool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WxGrouponServiceImpl implements WxGrouponService {

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public WxListVo list(int page, int size) {

        WxListVo listVo = new WxListVo();
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        int count = (int) grouponRulesMapper.countByExample(grouponRulesExample);
        List<GrouponRules> grouponRulesList = grouponRulesMapper.selectByExample(grouponRulesExample);
        List<WxGroupon> goodsList = new ArrayList<>();
        for (GrouponRules grouponRules : grouponRulesList) {
            WxGroupon wxGroupon = new WxGroupon();
            Integer goodsId = grouponRules.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            wxGroupon.setGoods(goods);
            wxGroupon.setGroupon_member(grouponRules.getDiscountMember());
            wxGroupon.setGroupon_price(goods.getRetailPrice().subtract(grouponRules.getDiscount()));
            goodsList.add(wxGroupon);
        }
        PageHelper.startPage(page, size);
        PageInfo info = new PageInfo(goodsList);
        listVo.setCount((int)info.getTotal());
        listVo.setData(info.getList());
        return listVo;
    }
}
