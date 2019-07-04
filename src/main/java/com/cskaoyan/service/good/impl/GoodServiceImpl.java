package com.cskaoyan.service.good.impl;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.CommentExample;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GoodsExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.CommentMapper;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.service.good.GoodService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public PageData getListData(int page, int limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        GoodsExample example = new GoodsExample();
        example.setOrderByClause(sort+" "+order);
        List<Goods> goods = goodsMapper.selectByExample(example);
        PageInfo<Comment> pageinfo = new PageInfo(goods);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public PageData getCommentListData(int page, int limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        CommentExample example = new CommentExample();
        example.setOrderByClause(sort+" "+order);
        List<Comment> goods = commentMapper.selectByExample(example);
        PageInfo<Comment> pageinfo = new PageInfo(goods);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }
}
