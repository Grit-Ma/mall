package com.cskaoyan.mall_wx.controller;
import com.cskaoyan.bean.*;
import com.cskaoyan.bean.System;
import com.cskaoyan.bean.vo.CartTotalVO;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_wx.service.CartService;
import com.cskaoyan.mall_wx.service.cdy.WxAddressService;
import com.cskaoyan.mall_wx.service.mtan.CouponVerifyService;
import com.cskaoyan.mall_wx.service.mtan.WxCouponService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("wx/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    WxAddressService wxAddressService;
    @Autowired
    WxCouponService wxCouponService;
    @Autowired
    CouponVerifyService couponVerifyService;



    //获取购物车数据，返回购物车list，cartTotal
    //需要传入用户id，未写
    @RequestMapping("index")
    @ResponseBody
    public ResponseVO cartIndex(HttpServletRequest request) {
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        Cart cart = new Cart();
        cart.setUserId(userId);
        Map check = new HashMap();

        List<Cart> carts = cartService.selectAll(cart);
        CartTotalVO cartTotal = cartService.getCartTotal(cart,check);

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
    @ResponseBody
    public ResponseVO addToCart(@RequestBody Cart cart,HttpServletRequest request){
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        cart.setUserId(userId);

        long num = (long) cartService.addToCart(cart,0).get("num");  //添加进购物车flag为0
        ResponseVO vo = new ResponseVO();
        vo.setData(num);
        vo.setErrmsg("成功");
        return vo;


    }



    //立即购买商品，商品详情页面点击立即购买,会与购物车内该商品合并，number以立即购买时选取的为准
    @RequestMapping("fastadd")
    @ResponseBody
    public ResponseVO fastadd(@RequestBody Cart cart, HttpServletRequest request){
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        cart.setUserId(userId);
        //添加进cart
        int cartId = (int) cartService.addToCart(cart,1).get("currentCartId");  //立即购买flag为1

        ResponseVO vo = new ResponseVO();
        vo.setData(cartId);
        vo.setErrmsg("成功");
        return vo;
    }


    //更新购物车的商品，购物车内编辑，增加或者减少已有商品的数量
    @RequestMapping("update")
    @ResponseBody
    public ResponseVO updateCart(@RequestBody Cart cart, HttpServletRequest request){
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        cart.setUserId(userId);

        cartService.updateCart(cart);
        ResponseVO vo = new ResponseVO();
        vo.setErrmsg("成功");
        return vo;
    }


    //删除购物车的商品，购物车内编辑，选中商品，删除所有选中商品，返回删除后index
    @RequestMapping("delete")
    @ResponseBody
    public ResponseVO deleteCart(@RequestBody Map productsIdMap, HttpServletRequest request){
        List productsId = ((List) productsIdMap.get("productIds"));
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        Cart cart = new Cart();
        cart.setUserId(userId);
        Map check = new HashMap();

        //删除操作
        cartService.deleteChecked(cart,productsId);

        //返回删除后的购物车
        List<Cart> carts = cartService.selectAll(cart);
        CartTotalVO cartTotal = cartService.getCartTotal(cart,check);

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
    //每选中一次，将该cart内该商品checked改为1，取消改为0
    //返回购物车list
    @RequestMapping("checked")
    @ResponseBody
    public ResponseVO checked(@RequestBody Map checked, HttpServletRequest request){
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        Cart cart = new Cart();
        cart.setUserId(userId);
        cartService.checked(cart,checked);

        //返回购物车list
        List<Cart> carts = cartService.selectAll(cart);
        CartTotalVO cartTotal = cartService.getCartTotal(cart,checked);
        Map data = new HashMap();
        data.put("cartList",carts);
        data.put("cartTotal",cartTotal);
        ResponseVO vo = new ResponseVO();
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;
    }


    //获取购物车商品件数,暂无
    @RequestMapping("goodscount")
    @ResponseBody
    public ResponseVO goodScount(HttpServletRequest request){
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        Cart cart = new Cart();
        cart.setUserId(userId);
        Map check = new HashMap();

        CartTotalVO cartTotal = cartService.getCartTotal(cart,check);
        long data = cartTotal.getGoodsCount();
        ResponseVO vo = new ResponseVO();
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;
    }


    //下单前信息确认，购物车结算或者立即购买都会发送此请求
    //cartId获取购物车的id，address地址id，couponId优惠券id，grouponRulesId团购id
    @RequestMapping("checkout")
    @ResponseBody
    public ResponseVO checkOut(int cartId, int addressId, int couponId, int grouponRulesId,HttpServletRequest request){
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        Cart cart = new Cart();
        cart.setUserId(userId);
        Map data = new HashMap();
        if (cartId == 0){
            Map check = new HashMap();
            check.put("cartId",cartId);
            check.put("addressId",addressId);
            check.put("couponId",couponId);
            check.put("grouponRulesId",grouponRulesId);


            //团购减免
            BigDecimal grouponPrice = cartService.GrouponPrice(grouponRulesId);
            //商品总价格，list--checkedGoodsList--goodsTotalPrice
            CartTotalVO cartTotalVO = cartService.getCartTotal(cart,check);
            BigDecimal goodsPrice = cartTotalVO.getCheckedGoodsAmount();
            if (grouponPrice != null){
                goodsPrice = goodsPrice.subtract(grouponPrice);
            }
            //当前使用的地址的信息
            AddressPackage address = wxAddressService.query(userId, addressId);
            //优惠券金额-couponPrice
            BigDecimal couponPrice = wxCouponService.couponPriceBycouponId(couponId);
            //可用于优惠券数量-availableCouponLength
            List<CouponUser> couponUserList = wxCouponService.queryAll(userId);
            List<Coupon> availableCouponList = new ArrayList<>(couponUserList.size());
            for (CouponUser couponUser : couponUserList) {
                Coupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), goodsPrice);
                if (coupon == null) {
                    continue;
                }
                availableCouponList.add(coupon);
            }
            int availableCouponLength = availableCouponList.size();

            //计算邮费,(goodsPrice)满设定值包邮
            BigDecimal freightPrice = cartService.freightPrice(goodsPrice);
            //实付价格与订单价格actualPrice与orderTotalPrice
            BigDecimal orderPrice = goodsPrice.add(freightPrice).subtract(couponPrice);
            BigDecimal actualPrice = orderPrice;

            data.put("actualPrice",actualPrice);  //订单总价：订单价格-团购优惠
            data.put("addressId",check.get("addressId"));  //收货地址id
            data.put("availableCouponLength",availableCouponLength);  //可用优惠券数量
            data.put("checkedAddress",address);    //当前使用地址信息
            data.put("checkedGoodsList",cartTotalVO.getCarts());  //需要购买的商品列表
            data.put("couponId",check.get("couponId"));  //当前使用优惠券id，无则为-1
            data.put("couponPrice",couponPrice);    //当前使用优惠券价格
            data.put("freightPrice",freightPrice);  //运费
            data.put("goodsTotalPrice",goodsPrice);  //商品总价
            data.put("grouponPrice",grouponPrice);  //团购减免
            data.put("grouponRulesId",check.get("grouponRulesId"));  //团购规则id
            data.put("orderTotalPrice",orderPrice);  //订单价格：商品价格+运费-优惠券
        }else {
            Map check = new HashMap();
            check.put("cartId",cartId);
            check.put("addressId",addressId);
            check.put("couponId",couponId);
            check.put("grouponRulesId",grouponRulesId);


            //团购减免
            BigDecimal grouponPrice = cartService.GrouponPrice(grouponRulesId);
            //商品总价格，list--checkedGoodsList--goodsTotalPrice
            CartTotalVO cartTotalVO = cartService.getCurrentCartTotal(cartId,check);
            BigDecimal goodsPrice = cartTotalVO.getCheckedGoodsAmount();
            if (grouponPrice != null){
                goodsPrice = goodsPrice.subtract(grouponPrice);
            }
            //当前使用的地址的信息
            AddressPackage address = wxAddressService.query(userId, addressId);
            //优惠券金额-couponPrice
            BigDecimal couponPrice = wxCouponService.couponPriceBycouponId(couponId);
            //可用于优惠券数量-availableCouponLength
            List<CouponUser> couponUserList = wxCouponService.queryAll(userId);
            List<Coupon> availableCouponList = new ArrayList<>(couponUserList.size());
            for (CouponUser couponUser : couponUserList) {
                Coupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), goodsPrice);
                if (coupon == null) {
                    continue;
                }
                availableCouponList.add(coupon);
            }
            int availableCouponLength = availableCouponList.size();
            //计算邮费,(goodsPrice)满设定值包邮
            BigDecimal freightPrice = cartService.freightPrice(goodsPrice);
            //实付价格与订单价格actualPrice与orderTotalPrice
            BigDecimal orderPrice = goodsPrice.add(freightPrice).subtract(couponPrice);
            BigDecimal actualPrice = orderPrice;

            data.put("actualPrice",actualPrice);  //订单总价：订单价格-团购优惠
            data.put("addressId",check.get("addressId"));  //收货地址id
            data.put("availableCouponLength",availableCouponLength);  //可用优惠券数量
            data.put("checkedAddress",address);    //当前使用地址信息
            data.put("checkedGoodsList",cartTotalVO.getCarts());  //需要购买的商品列表
            data.put("couponId",check.get("couponId"));  //当前使用优惠券id，无则为-1
            data.put("couponPrice",couponPrice);    //当前使用优惠券价格
            data.put("freightPrice",freightPrice);  //运费
            data.put("goodsTotalPrice",goodsPrice);  //商品总价
            data.put("grouponPrice",grouponPrice);  //团购减免
            data.put("grouponRulesId",check.get("grouponRulesId"));  //团购规则id
            data.put("orderTotalPrice",orderPrice);  //订单价格：商品价格+运费-优惠券
        }

        ResponseVO vo = new ResponseVO();
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;
    }

}