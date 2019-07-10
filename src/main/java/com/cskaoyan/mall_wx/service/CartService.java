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

    //立即购买，获取当前商品cart
    CartTotalVO getCurrentCartTotal(int cartId,Map checked);

    //添加商品进购物车
    Map addToCart(Cart cart, int flag);

    //更新购物车商品
    void updateCart(Cart cart);

    //编辑删除选中商品
    void deleteChecked(Cart cart,List productsId);

    //选中商品或者取消选中
    void checked(Cart cart, Map checked);

    List<Cart> getCheckedCartGood(int userId);

    void clearCart(int userId);


    //计算邮费
    BigDecimal freightPrice(BigDecimal goodsPrice);


}