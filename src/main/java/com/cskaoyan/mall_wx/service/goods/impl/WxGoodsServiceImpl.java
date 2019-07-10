package com.cskaoyan.mall_wx.service.goods.impl;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.vo.*;
import com.cskaoyan.mall_wx.service.goods.WxGoodsService;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WxGoodsServiceImpl implements WxGoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;
    @Autowired
    SearchHistoryMapper searchHistoryMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    FootprintMapper footprintMapper;


    @Override
    public GoodsListVo getWxListData(Integer categoryId, int page, int size, String keyword, String sort, String order, Integer userId, Integer brandId) {
        GoodsListVo vo = new GoodsListVo();
        PageHelper.startPage(page,size);
        if(brandId != null){// 专题页面
            vo = goodsMapper.selectGoodsListByBrandId(brandId);
            vo.setCount(vo.getGoodsList().size());
            return vo;
        }
        List<WxGoodsVo> goodsList = null;
        List<Category> categories = null;
        if(keyword != null && !keyword.isEmpty()){
            goodsList = goodsMapper.selectLikeGoodsName("%" + keyword + "%", sort + " " + order);
            categories = categoryMapper.selectLikeGoodsName("%" + keyword + "%");
            addSearchHistory(keyword, userId);
        }else {
            goodsList = goodsMapper.selectWxGoodsByCategoryId(categoryId);
            categories = categoryMapper.selectFilterCategoryList(categoryId);
        }
        PageInfo<WxGoodsVo> pageinfo = new PageInfo(goodsList);
        vo.setGoodsList(pageinfo.getList());
        vo.setFilterCategoryList(categories);
        vo.setCount(pageinfo.getSize());
        return vo;

    }


    @Override
    public CategoryVo getCategory(int id) {
        Category parentCategory = null;
        CategoryExample example = new CategoryExample();
        example.createCriteria().andIdEqualTo(id).andDeletedEqualTo(false);
        Category currentCategory = categoryMapper.selectByExample(example).get(0);
        int pid = currentCategory.getPid();
        if (currentCategory.getPid() == 0){// 一级
            parentCategory = currentCategory;
            pid = parentCategory.getId();
        }else {
            parentCategory = categoryMapper.selectByPrimaryKey(currentCategory.getPid());
        }
        example.clear();

        example.createCriteria().andPidEqualTo(pid).andDeletedEqualTo(false);
        List<Category> brotherCategory = categoryMapper.selectByExample(example);
        if(currentCategory.getPid() == 0){
            currentCategory = brotherCategory.size() > 0 ? brotherCategory.get(0) : null;
        }
        CategoryVo vo = new CategoryVo(brotherCategory, currentCategory, parentCategory);
        return vo;
    }

    @Override
    public GoodsDeatil getDetail(int goodsId, Integer userId) {
        GoodsDeatil detail = new GoodsDeatil();
        GoodsAttributeExample example = new GoodsAttributeExample();
        example.createCriteria().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        detail.setAttributes(goodsAttributeMapper.selectByExample(example));
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIdEqualTo(goodsId).andDeletedEqualTo(false);
        List<Goods> goods = this.goodsMapper.selectByExample(goodsExample);
        if(goods.size() > 0){
            detail.setInfo(goods.get(0));
        }
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andIdEqualTo(detail.getInfo().getBrandId()).andDeletedEqualTo(false);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        if(!brands.isEmpty()){
            detail.setBrand(brands.get(0));
        }
        CommentVo commentVo = commentMapper.selectCommentVoByValueId(goodsId);
        if(commentVo != null){
            commentVo.setCount(commentVo.getData().size());
        }
        detail.setComment(commentVo);
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andDeletedEqualTo(false);
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        detail.setIssue(issues);
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);
        detail.setProductList(goodsProducts);
        List<Specification> goodsSpecifications = goodsSpecificationMapper.selectSpecificationList(goodsId);
        detail.setSpecificationList(goodsSpecifications);
        detail.setShareImage(detail.getInfo().getShareUrl());
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        grouponRulesExample.createCriteria().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        detail.setGroupon(grouponRules);
        CollectExample collectExample = new CollectExample();
        if(userId != null){
            collectExample.createCriteria().andTypeEqualTo(Byte.valueOf("0")).andUserIdEqualTo(userId)
                    .andValueIdEqualTo(detail.getInfo().getId()).andDeletedEqualTo(false);
            List<Collect> collects = collectMapper.selectByExample(collectExample);
            if(collects.size() > 0){
                detail.setUserHasCollect(1);
            }else {
                detail.setUserHasCollect(0);
            }
            addFootPrint(userId,goodsId);
        }
        return detail;
    }

    @Override
    public HashMap getCount() {
        int cnt = goodsMapper.getGoodsCount();
        HashMap<String, Object> map = new HashMap<>();
        map.put("goodsCount",cnt);
        return map;
    }

    @Override
    public HashMap commentCount(int valueId, int type) {
        HashMap<String,Object> comments = commentMapper.getCommentCount(valueId,type);
        return comments;
    }

    @Override
    public HashMap commentList(int valueId, int type, int size, int page, int showType) {
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(page,size);
        List<GoodsCommentVo> goodsCommentVo = commentMapper.selectCommentByValueIdAndTypeAndShowType(valueId,type,showType);
        PageInfo<GoodsCommentVo> pageinfo = new PageInfo(goodsCommentVo);
        map.put("count",pageinfo.getTotal());
        map.put("currentPage",pageinfo.getPageNum());
        map.put("data",pageinfo.getList());
        return map;
    }

    @Override
    public List<WxGoodsVo> getRelated(int id) {
        PageHelper.startPage(1,6);
        int[] keys = footprintMapper.selectRelated();
        List<WxGoodsVo> list = goodsMapper.selectByPrimaryKeys(keys);
        return list;
    }

    private void addSearchHistory(String keyword,Integer userId){
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setFrom("wx");
        searchHistory.setKeyword(keyword);
        searchHistory.setUserId(userId);
        if(userId != null){
            searchHistoryMapper.insert(searchHistory);
        }
    }

    private void addFootPrint(Integer userId,Integer goodsId){
        Footprint footprint = new Footprint();
        footprint.setGoodsId(goodsId);
        footprint.setUserId(userId);
        if(userId != null){
            footprintMapper.insert(footprint);
        }
    }
}
