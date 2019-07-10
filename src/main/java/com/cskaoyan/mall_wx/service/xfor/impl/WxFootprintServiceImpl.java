package com.cskaoyan.mall_wx.service.xfor.impl;

import com.cskaoyan.bean.Footprint;
import com.cskaoyan.bean.FootprintExample;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.wx.xfor.FootprintList;
import com.cskaoyan.converter.DateConverter;
import com.cskaoyan.mall_wx.service.xfor.WxFootprintService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import com.cskaoyan.mapper.FootprintMapper;
import com.cskaoyan.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WxFootprintServiceImpl implements WxFootprintService {
    @Autowired
    FootprintMapper footprintMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public FootprintList getFootprintList(HttpServletRequest request, int page, int size) {
        String token = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(token);
        FootprintExample footprintExample = new FootprintExample();
        footprintExample.createCriteria().andUserIdEqualTo(userId);
        PageHelper.startPage(page, size);
        List<Footprint> footprints = footprintMapper.selectByExample(footprintExample);
        ArrayList goodsArrayList = new ArrayList<>();
        for (Footprint fp : footprints) {
            Goods goods = goodsMapper.selectByPrimaryKey(fp.getGoodsId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("addTime", DateConverter.getStringDate(fp.getAddTime()));
            map.put("brief",goods.getBrief());
            map.put("goodsId",goods.getId());
            map.put("name",goods.getName());
            map.put("picUrl",goods.getPicUrl());
            map.put("retailPrice",goods.getRetailPrice());
            goodsArrayList.add(map);
        }
        FootprintList footprintList = new FootprintList();
        footprintList.setFootprintList(goodsArrayList);
        footprintList.setTotalPages(footprintMapper.countByExample(new FootprintExample()));
        return footprintList;
    }
}
