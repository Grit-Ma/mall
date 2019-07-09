package com.cskaoyan.mall_wx.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.vo.CartTotalVO;
import com.cskaoyan.mapper.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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

    //获取购物车数据
    @Override
    public List<Cart> selectAll(Cart cart) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(cart.getUserId());

        List<Cart> carts = cartMapper.selectByExample(example);
        return carts;
    }

    @Override
    public List<Integer> selectAllGoodId(Cart cart) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(cart.getUserId());

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
        criteria1.andUserIdEqualTo(cart.getUserId());
        if (cartMapper.sumAll(cart.getUserId()) == null){  //购物车商品总价格
            cartTotal.setGoodsAmount(0);
        }else {
            cartTotal.setGoodsAmount(cartMapper.sumAll(cart.getUserId()));
        }
        cartTotal.setGoodsCount(cartMapper.countByExample(example1));       //购物车商品总数量


        CartExample example2 = new CartExample();
        CartExample.Criteria criteria2 = example1.createCriteria();
        criteria2.andUserIdEqualTo(cart.getUserId()).andCheckedEqualTo(true);
        if (cartMapper.sumAllChecked(cart.getUserId()) == null){  //选中商品总价格
            cartTotal.setCheckedGoodsAmount(0);
        }else {
            int d = cartMapper.sumAllChecked(cart.getUserId());
            cartTotal.setCheckedGoodsAmount(d);
        }
        cartTotal.setCheckedGoodsCount(cartMapper.countByExample(example2));     //选中商品总数量
        cartTotal.setCarts(cartMapper.selectByExample(example1));  //选中商品列表


        return cartTotal;
    }

    //添加商品进购物车,分别用goodsid，productid取对应数据，填满cart
    //先判断是否已有此商品
    @Override
    public long addToCart(Cart cart) {
        int userId = cart.getUserId();
        int goodsId = cart.getGoodsId();
        int productId = cart.getProductId();
        int number = cart.getNumber();
        //判断是否已存在
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId);
        List<Cart> carts = cartMapper.selectByExample(example);


        if (carts.isEmpty()){  //不存在就填满cart存进去
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(productId);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName(goods.getName());
            cart.setPrice(goodsProduct.getPrice());
            cart.setSpecifications(goodsProduct.getSpecifications());
            cart.setPicUrl(goodsProduct.getUrl());
            cart.setAddTime(goods.getAddTime());
            cart.setUpdateTime(goods.getUpdateTime());
            cart.setDeleted(goods.getDeleted());
            cartMapper.insert(cart);
        }else {  //存在就找出num，加上number。
            int cartNumber = carts.get(0).getNumber();
            carts.get(0).setNumber((short) (cartNumber+number));
            cartMapper.updateByExampleSelective(carts.get(0),example);
        }
        CartExample example1 = new CartExample();
        CartExample.Criteria criteria2 = example1.createCriteria();
        criteria2.andUserIdEqualTo(userId);
        long num = cartMapper.countByExample(example1);
        return num;
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
        cartMapper.deleteChecked(cart.getUserId(),productsId);
    }


    //购物车内选中取消选中商品
    @Override
    public void checked(Cart cart, Map checked) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();

<<<<<<< HEAD
        criteria.andUserIdEqualTo(cart.getUserId()).andProductIdIn(productIds);
=======
        criteria.andUserIdEqualTo(cart.getUserId()).andProductIdIn((List<Integer>) checked.get("productIds"));
>>>>>>> 2495088e507ff2ad2f023b0d8c2b3953b5997e3f

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
        cartExample.createCriteria().andUserIdEqualTo(userId).andCheckedEqualTo(true);
        return cartMapper.selectByExample(cartExample);
    }

    //订单确认
    @Override
    public Map checkout(Cart cart) {
        Map check = new HashMap();
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();

        return null;
    }




}
