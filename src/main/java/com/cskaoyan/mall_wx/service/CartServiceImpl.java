package com.cskaoyan.mall_wx.service;

import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.CartExample;
import com.cskaoyan.bean.vo.CartTotalVO;
import com.cskaoyan.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartMapper cartMapper;

    //获取购物车数据
    @Override
    public List<Cart> selectAll(Cart cart) {
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(cart.getUserId());

        List<Cart> carts = cartMapper.selectByExample(example);
        return carts;
    }


    //获取购物车数据
    @Override
    public CartTotalVO getCartTotal(Cart cart) {
        CartTotalVO cartTotal = new CartTotalVO();

        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(cart.getUserId());

        cartTotal.setCheckedGoodsAmount(0);
        cartTotal.setCheckedGoodsCount(0);
        cartTotal.setGoodsAmount(cartMapper.sumAll(cart.getUserId()));
        cartTotal.setGoodsCount(cartMapper.countByExample(example));

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
        criteria.andIdEqualTo(cart.getId());
        criteria.andUserIdEqualTo(cart.getUserId());

        cartMapper.updateByExample(cart,example);
    }


    //删除选中商品
    @Override
    public void deleteChecked(Cart cart,int[] productsId) {
        cartMapper.deleteChecked(cart.getUserId(),productsId);
    }


}
