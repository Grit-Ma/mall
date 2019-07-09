package com.cskaoyan.mall_wx.service;

import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.bean.vo.CartTotalVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CartService {

    //获取购物车数据
    List<Cart> selectAll(Cart cart);

    //根据userId获取List<Cart>中的goodId
    List<Integer> selectAllGoodId(Cart cart);

    //获取购物车数据
    CartTotalVO getCartTotal(Cart cart,Map checked);


    //添加商品进购物车
    long addToCart(Cart cart);

    //更新购物车商品
    void updateCart(Cart cart);

    //编辑删除选中商品
    void deleteChecked(Cart cart,List productsId);

    //选中商品或者取消选中
    void checked(Cart cart, Map checked);

    //订单确认
    Map checkout(Cart cart);

    //查询所使用的优惠券byId
    Coupon coupon(int couponId);

    //下单清空




}