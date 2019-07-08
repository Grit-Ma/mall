package com.cskaoyan.mall_admin.service.good.impl;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.vo.CatAndBrandVo;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ProductVo;
import com.cskaoyan.mapper.*;
import com.cskaoyan.mall_admin.service.good.GoodService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.*;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;

    @Override
    public PageData getListData(int page, int limit, String sort, String order,String goodsSn,String name) {
        PageHelper.startPage(page,limit);
        GoodsExample example = new GoodsExample();
        example.setOrderByClause(sort+" "+order);
        GoodsExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsSnLike("%"+goodsSn+"%").andNameLike("%"+name+"%").andDeletedEqualTo(false);
        List<Goods> goods = goodsMapper.selectByExample(example);
        PageInfo<Goods> pageinfo = new PageInfo(goods);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public PageData getCommentListData(int page, int limit, String sort, String order, String userId, String valueId) throws InvalidParameterException {
        PageHelper.startPage(page,limit);
        CommentExample example = new CommentExample();
        example.setOrderByClause(sort+" "+order);
        CommentExample.Criteria criteria = example.createCriteria();
        if(userId != null){
            try{
                criteria.andUserIdEqualTo(Integer.valueOf(userId));
            }catch (NumberFormatException e){
                throw new InvalidParameterException("参数错误：userId = "+ userId);
            }
        }
        if(valueId != null && !valueId.isEmpty()){
            try{
                criteria.andValueIdEqualTo(Integer.valueOf(valueId));
            }catch (NumberFormatException e){
                throw new InvalidParameterException("参数错误：valueId = "+ valueId);
            }
        }
        List<Comment> goods = commentMapper.selectByExample(example);
        PageInfo<Comment> pageinfo = new PageInfo(goods);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public ProductVo getGoodById(int id) {
        GoodsInfo info = goodsMapper.getGoodsInfo(id);
        List categoriesIds = getCategoriesIds(info.getGoods().getCategoryId());
        ProductVo productVo = new ProductVo(info.getAttributes(), categoriesIds, info.getGoods(), info.getProducts(), info.getSpecifications());
        return productVo;
    }

    private List getCategoriesIds(Integer categoryId) {
        //只可用于两级分类
        HashMap map = categoryMapper.getCategoryIds(categoryId);
        ArrayList<Integer> list = new ArrayList<>();
        list.add((Integer) map.get("ida"));
        list.add((Integer) map.get("idb"));
        return list;
    }

    @Override
    public CatAndBrandVo getCatAndBrand() {
        List categoryList = getCategoryList();
        return new CatAndBrandVo(getBrandList(), categoryList);
    }

    @Override
    @Transactional
    public int update(GoodsInfo goodsInfo) {
        Integer goodsId = goodsInfo.getGoods().getId();
        updateGoods(goodsInfo.getGoods());
        updateAttribute(goodsInfo.getAttributes(),goodsId);
        updateProducts(goodsInfo.getProducts(),goodsId);
        updateSpecifications(goodsInfo.getSpecifications(),goodsId);
        return 0;
    }

    @Override
    public void delete(Goods goods) {
        goods.setDeleted(true);
        goodsMapper.updateByPrimaryKey(goods);
    }

    @Override
    public void delete(Comment comment) {
        comment.setDeleted(true);
        commentMapper.updateByPrimaryKey(comment);
    }

    @Override
    @Transactional
    public void create(GoodsInfo goodsInfo) {
        int goodsId = createGoods(goodsInfo.getGoods());
        createAttributes(goodsInfo.getAttributes(),goodsId);
        createProducts(goodsInfo.getProducts(),goodsId);
        createSpecifications(goodsInfo.getSpecifications(),goodsId);
    }

    @Override
    public int check(GoodsInfo goodsInfo) {
        List<GoodsAttribute> attributes = goodsInfo.getAttributes();
        for(GoodsAttribute attribute : attributes){
            if(attribute.getValue() == null) {
                return 401;
            }
        }
        return 0;
    }

    private void createSpecifications(List<GoodsSpecification> specifications, int goodsId) {
        for(GoodsSpecification specification : specifications){
            specification.setGoodsId(goodsId);
            goodsSpecificationMapper.insert(specification);
        }
    }

    private void createProducts(List<GoodsProduct> products, int goodsId) {
        for(GoodsProduct product : products){
            product.setGoodsId(goodsId);
            goodsProductMapper.insert(product);
        }
    }

    private int createGoods(Goods goods) {
        goodsMapper.insert(goods);
        return goods.getId();
    }

    private void createAttributes(List<GoodsAttribute> attributes, int goodsId) {
        for(GoodsAttribute goodsAttribute : attributes){
            goodsAttribute.setGoodsId(goodsId);
            goodsAttributeMapper.insert(goodsAttribute);
        }
    }

    private void updateSpecifications(List<GoodsSpecification> goodsSpecifications, Integer goodsId) {
        GoodsSpecificationExample example = new GoodsSpecificationExample();
        GoodsSpecificationExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        GoodsSpecification att = new GoodsSpecification();
        att.setDeleted(true);
        goodsSpecificationMapper.updateByExampleSelective(att,example);
        for(GoodsSpecification goodsSpecification : goodsSpecifications){
            if(goodsSpecification.getGoodsId() == null){
                goodsSpecification.setGoodsId(goodsId);
                goodsSpecificationMapper.insert(goodsSpecification);
            }else {
                goodsSpecificationMapper.updateByPrimaryKeySelective(goodsSpecification);
            }
        }
    }

    private void updateProducts(List<GoodsProduct> products, Integer goodsId) {
        GoodsProductExample example = new GoodsProductExample();
        GoodsProductExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        GoodsProduct att = new GoodsProduct();
        att.setDeleted(true);
        goodsProductMapper.updateByExampleSelective(att,example);
        for(GoodsProduct product : products){
            if(product.getGoodsId() == null){
                product.setGoodsId(goodsId);
                goodsProductMapper.insert(product);
            }else {
                goodsProductMapper.updateByPrimaryKeySelective(product);
            }
        }

    }

    private void updateGoods(Goods goods) {
        goodsMapper.updateByPrimaryKey(goods);
    }

    /**
     * 先逻辑删除，后提交更新
     */
    private void updateAttribute(List<GoodsAttribute> attributes,int goodsId){
        GoodsAttributeExample example = new GoodsAttributeExample();
        GoodsAttributeExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        GoodsAttribute att = new GoodsAttribute();
        att.setDeleted(true);
        goodsAttributeMapper.updateByExampleSelective(att,example);
        for(GoodsAttribute attribute : attributes){
            if(attribute.getGoodsId() == null){
                attribute.setGoodsId(goodsId);
                goodsAttributeMapper.insert(attribute);
            }else {
                goodsAttributeMapper.updateByPrimaryKeySelective(attribute);
            }
        }
    }

    private List getCategoryList(){
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("pid");//排序以保证完全载入
        HashMap<String, HashMap<String,Object>> map = new HashMap<>();
        List<Category> categories = categoryMapper.selectByExample(example);
        for(Category category : categories){
            HashMap<String, Object> tMap = new HashMap<>();
            tMap.put("label",category.getName());
            tMap.put("value",category.getId());
            if(category.getPid() == 0) {
                tMap.put("children",new ArrayList<>());
                map.put(category.getId().toString(),tMap);
            }else {
                List children = (List) map.get(category.getPid().toString()).get("children");
                children.add(tMap);
            }
        }
        List categoryList = new ArrayList();
        Set<String> keys = map.keySet();
        for(String key : keys){
            categoryList.add(map.get(key));
        }
        return categoryList;
    }

    private List getBrandList(){
        ArrayList<HashMap> list = new ArrayList<>();
        List<Brand> brands = brandMapper.selectByExample(new BrandExample());
        for(Brand brand : brands) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("label",brand.getName());
            map.put("value",brand.getId());
            list.add(map);
        }
        return list;
    }

}
