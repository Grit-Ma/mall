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
        cartTotal.setGoodsAmount(cartMapper.sumAll(cart.getUserId()));    //购物车商品总价格
        cartTotal.setGoodsCount(cartMapper.countByExample(example1));       //购物车商品总数量


        CartExample example2 = new CartExample();
        CartExample.Criteria criteria2 = example1.createCriteria();
        criteria2.andUserIdEqualTo(cart.getUserId()).andCheckedEqualTo(true);
        cartTotal.setCheckedGoodsAmount(cartMapper.sumAllChecked(cart.getUserId()));   //选中商品总价格
        cartTotal.setCheckedGoodsCount(cartMapper.countByExample(example2));     //选中商品总数量
        cartTotal.setCarts(cartMapper.selectByExample(example1));  //选中商品列表


        return cartTotal;
    }

    //添加商品进购物车
    @Override
    public long addToCart(Cart cart) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(cart.getUserId());

        long num = cartMapper.countByExample(example);
        return num;
    }


    //更新购物车商品
    @Override
    public void updateCart(Cart cart) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(cart.getId()).andUserIdEqualTo(cart.getUserId());

        cartMapper.updateByExample(cart,example);
    }


    //删除选中商品
    @Override
    public void deleteChecked(Cart cart,int[] productsId) {
        cartMapper.deleteChecked(cart.getUserId(),productsId);
    }


    //购物车内选中取消选中商品
    @Override
    public void checked(Cart cart, Map checked) {
        Integer [] productId = (Integer[]) checked.get("productIds");
        List<Integer> productIds = Arrays.asList(productId);

        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();

        criteria.andUserIdEqualTo(cart.getUserId()).andProductIdIn(productIds).;

        List<Cart> carts = cartMapper.selectByExample(example);
        for (Cart c : carts) {
            c.setChecked(((int) checked.get("isChecked") == 1)?true:false);
            cartMapper.updateByPrimaryKey(c);
        }

    }


    //订单确认
    @Override
    public Map checkout(Cart cart) {
        Map check = new HashMap();
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();

        return null;
    }


    //查询优惠券
    @Override
    public Coupon coupon(int couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        return coupon;
    }


    //可用优惠券列表


}
