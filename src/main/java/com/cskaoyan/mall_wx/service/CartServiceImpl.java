package com.cskaoyan.mall_wx.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.System;
import com.cskaoyan.bean.vo.CartTotalVO;
import com.cskaoyan.mapper.*;
import com.cskaoyan.mapper.config.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartMapper cartMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CouponUserMapper couponUserMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    SystemMapper systemMapper;

    //获取购物车数据
    @Override
    public List<Cart> selectAll(Cart cart) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(cart.getUserId()).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(example);
        return carts;
    }

    @Override
    public List<Integer> selectAllGoodId(Cart cart) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(cart.getUserId()).andDeletedEqualTo(false);

        List<Cart> carts = cartMapper.selectByExample(example);
        List<Integer> goodIds = new ArrayList<>();
        for (Cart cart1 : carts) {
            goodIds.add(cart1.getGoodsId());
        }
        return goodIds;
    }


    //获取购物车数据
    @Override
    public CartTotalVO getCartTotal(Cart cart,Map checked) {
        CartTotalVO cartTotal = new CartTotalVO();

        CartExample example1 = new CartExample();
        CartExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andUserIdEqualTo(cart.getUserId()).andDeletedEqualTo(false);
        if (cartMapper.sumAll(cart.getUserId()) == null){  //购物车商品总价格
            cartTotal.setGoodsAmount(0);
        }else {
            cartTotal.setGoodsAmount(cartMapper.sumAll(cart.getUserId()));
        }

        List<Cart> carts = cartMapper.selectByExample(example1);
        int num1 = 0;
        for (Cart cart1 : carts) {
            num1 += cart1.getNumber();
        }
        cartTotal.setGoodsCount(num1);       //购物车商品总数量


        CartExample example2 = new CartExample();
        CartExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andUserIdEqualTo(cart.getUserId()).andCheckedEqualTo(true).andDeletedEqualTo(false);
        if (cartMapper.sumAllChecked(cart.getUserId()) == null){  //选中商品总价格
            cartTotal.setCheckedGoodsAmount(new BigDecimal(0));
        }else {
            BigDecimal d = cartMapper.sumAllChecked(cart.getUserId());
            cartTotal.setCheckedGoodsAmount(d);
        }
        List<Cart> carts2 = cartMapper.selectByExample(example2);
        int num2 = 0;
        for (Cart cart1 : carts2) {
            num2 += cart1.getNumber();
        }
        cartTotal.setCheckedGoodsCount(num2);     //选中商品总数量
        cartTotal.setCarts(cartMapper.selectByExample(example2));  //选中商品列表


        return cartTotal;
    }

    //添加商品进购物车,分别用goodsid，productid取对应数据，填满cart
    //先判断是否已有此商品
    @Override
    public Map addToCart(Cart cart,int flag) {
        int userId = cart.getUserId();
        int goodsId = cart.getGoodsId();
        int productId = cart.getProductId();
        int number = cart.getNumber();
        //判断是否已存在
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(example);


        if (carts.isEmpty()){  //不存在就填满cart存进去
            GoodsExample example2 = new GoodsExample();
            GoodsExample.Criteria criteria1 = example2.createCriteria();
            criteria1.andIdEqualTo(goodsId).andDeletedEqualTo(false);
            Goods goods = goodsMapper.selectByExample(example2).get(0);
            GoodsProductExample example3 = new GoodsProductExample();
            GoodsProductExample.Criteria criteria3 = example3.createCriteria();
            criteria1.andIdEqualTo(productId).andDeletedEqualTo(false);
            GoodsProduct goodsProduct = goodsProductMapper.selectByExample(example3).get(0);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName(goods.getName());
            cart.setPrice(goodsProduct.getPrice());
            cart.setSpecifications(goodsProduct.getSpecifications());
            cart.setPicUrl(goodsProduct.getUrl());
            cart.setAddTime(goods.getAddTime());
            cart.setUpdateTime(goods.getUpdateTime());
            cart.setDeleted(false);
            cartMapper.insert(cart);
        }else if (carts.get(0).getDeleted().equals(true)){    //存在且逻辑已删除
            carts.get(0).setNumber((short) (number));
            cartMapper.updateByExampleSelective(carts.get(0),example);
        }else {    //存在且逻辑尚未删除就判断，是添加进购物车还是立即购买，
                   //进购物车就是找出num，加上number。
                   //立即购买则是更新number
            if (flag == 0) {
                int cartNumber = carts.get(0).getNumber();
                carts.get(0).setNumber((short) (cartNumber+number));
                cartMapper.updateByExampleSelective(carts.get(0),example);
            }else if (flag == 1){
                carts.get(0).setNumber((short) (number));
                cartMapper.updateByExampleSelective(carts.get(0),example);
            }

        }
        //购物车商品总数量
        CartExample example1 = new CartExample();
        CartExample.Criteria criteria2 = example1.createCriteria();
        criteria2.andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Cart> carts1 = cartMapper.selectByExample(example1);
        long num = 0;
        for (Cart cart1 : carts1) {
            num += cart1.getNumber();
        }
        //返回购物车商品总数量和当前添加的cartId
        Map map = new HashMap();
        map.put("num",num);
        map.put("currentCartId", cartMapper.selectByExample(example).get(0).getId());
        return map;
    }


    //更新购物车商品
    @Override
    public void updateCart(Cart cart) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(cart.getId()).andUserIdEqualTo(cart.getUserId());
        Cart cart1 = cartMapper.selectByPrimaryKey(cart.getId());
        cart1.setNumber(cart.getNumber());
        cartMapper.updateByExample(cart1,example);
    }


    //删除选中商品
    @Override
    public void deleteChecked(Cart cart,List productsId) {
        cart.setDeleted(true);
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdIn(productsId);
        cartMapper.updateByExampleSelective(cart,example);
    }


    //购物车内选中取消选中商品
    @Override
    public void checked(Cart cart, Map checked) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(cart.getUserId()).andProductIdIn((List<Integer>) checked.get("productIds")).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(example);
        for (Cart c : carts) {
            c.setChecked(((int) checked.get("isChecked") == 1)?true:false);
            cartMapper.updateByPrimaryKey(c);
        }
    }

    //根据userId获取所有选中的cart表中条目
    @Override
    public List<Cart> getCheckedCartGood(int userId){
        CartExample cartExample =new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.selectByExample(cartExample);
    }

    @Override
    public void clearCart(int userId){
        List<Cart> checkedCartGood = getCheckedCartGood(userId);
        for(Cart c:checkedCartGood) {
            c.setDeleted(true);
            cartMapper.updateByPrimaryKeySelective(c);
        }
    }


    //计算运费
    @Override
    public BigDecimal freightPrice(BigDecimal goodsPrice) {
        SystemExample e = new SystemExample();
        e.createCriteria().andKeyNameEqualTo("cskaoyan_mall_express_freight_min");
        List<System> systems = systemMapper.selectByExample(e);
        BigDecimal freightPrice = new BigDecimal(0.00);
        if (goodsPrice.compareTo(new BigDecimal(systems.get(0).getKeyValue())) < 0) {
            e = new SystemExample();
            e.createCriteria().andKeyNameEqualTo("cskaoyan_mall_express_freight_value");
            freightPrice = new BigDecimal(systemMapper.selectByExample(e).get(0).getKeyValue());
        }

        return  freightPrice;
    }


}
