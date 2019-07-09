package com.cskaoyan.mall_wx.controller;
import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.CartExample;
import com.cskaoyan.bean.vo.CartTotalVO;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_wx.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("cart")
public class CartController {

    @Autowired
    CartService cartService;


    //获取购物车数据，返回购物车list，cartTotal
    //需要传入用户id，未写
    @RequestMapping("index")
    public ResponseVO cartIndex() {

        Cart cart = new Cart();
        cart.setUserId(1);

        List<Cart> carts = cartService.selectAll(cart);
        CartTotalVO cartTotal = cartService.getCartTotal(cart);

        Map data = new HashMap();
        data.put("cartList",carts);
        data.put("cartTotal",cartTotal);

        ResponseVO vo = new ResponseVO();
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;
    }



    //添加商品到购物车，商品详情页点击加入购物车,返回data为购物车商品数量
    @RequestMapping("add")
    public ResponseVO addToCart(@RequestBody Cart cart){

        cart.setUserId(1);

        long num = cartService.addToCart(cart);
        ResponseVO vo = new ResponseVO();
        vo.setData(num);
        vo.setErrmsg("成功");
        return vo;


    }



    //立即购买商品，商品详情页面点击立即购买。
    //@RequestMapping("fastadd")



    //更新购物车的商品，购物车内编辑，增加或者减少已有商品的数量
    @RequestMapping("update")
    public ResponseVO updateCart(@RequestBody Cart cart){

        cart.setUserId(1);

        cartService.updateCart(cart);
        ResponseVO vo = new ResponseVO();
        vo.setErrmsg("成功");
        return vo;
    }


    //删除购物车的商品，购物车内编辑，选中商品，删除所有选中商品，返回删除后index
    @RequestMapping("delete")
    public ResponseVO deleteCart(@RequestBody int[] productsId){

        Cart cart = new Cart();
        cart.setUserId(1);

        //删除操作
        cartService.deleteChecked(cart,productsId);

        //返回删除后的购物车
        List<Cart> carts = cartService.selectAll(cart);
        CartTotalVO cartTotal = cartService.getCartTotal(cart);

        Map data = new HashMap();
        data.put("cartList",carts);
        data.put("cartTotal",cartTotal);

        ResponseVO vo = new ResponseVO();
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;


    }

    //选择或取消选择商品，购物车内直接选中或取消商品，点一次发送请求一次，点击全选发送一次
    //点击选择，返回已选商品总价，总数量，购物车内总商品，总价
    //@RequestMapping("checked")



    //获取购物车商品件数
    //@RequestMapping("goodscount")


    //下单前信息确认，购物车结算或者立即购买都会发送此请求
    //@RequestMapping("checkout")


}