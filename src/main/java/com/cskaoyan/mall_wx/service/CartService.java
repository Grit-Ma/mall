package com.cskaoyan.mall_wx.service;

import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.vo.CartTotalVO;

import java.util.List;

public interface CartService {

    //获取购物车数据
    List<Cart> selectAll(Cart cart);

    //获取购物车数据
    CartTotalVO getCartTotal(Cart cart);

    //添加商品进购物车
    long addToCart(Cart cart);

    //更新购物车商品
    void updateCart(Cart cart);

    //删除选中商品
    void deleteChecked(Cart cart,int[] productsId);
}