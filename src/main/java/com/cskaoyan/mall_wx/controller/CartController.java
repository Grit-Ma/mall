package com.cskaoyan.mall_wx.controller;
import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.CartExample;
import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.vo.CartTotalVO;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_wx.service.CartService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("wx/cart")
public class CartController {

    @Autowired
    CartService cartService;


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

        long num = cartService.addToCart(cart);
        ResponseVO vo = new ResponseVO();
        vo.setData(num);
        vo.setErrmsg("成功");
        return vo;


    }



    //立即购买商品，商品详情页面点击立即购买。
    //@RequestMapping("fastadd")
    //@ResponseBody


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
    public ResponseVO deleteCart(@RequestBody int[] productsId, HttpServletRequest request){
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


    /*//下单前信息确认，购物车结算或者立即购买都会发送此请求
    //cartId获取购物车的id，address地址id，couponId优惠券id，grouponRulesId团购id
    @RequestMapping("checkout")
    @ResponseBody
    public ResponseVO checkOut(@RequestBody Map check){

        Cart cart = new Cart();
        cart.setUserId(1);

        //商品总价格，list--checkedGoodsList--goodsTotalPrice
        CartTotalVO cartTotalVO = cartService.getCartTotal(cart,check);
        //优惠券金额-couponPrice
        BigDecimal couponPrice = cartService.coupon(cart,(int) check.get("couponId")).getDiscount();
        //actualPrice与orderTotalPrice
        BigDecimal actualPrice = BigDecimal.valueOf(cartTotalVO.getCheckedGoodsAmount()).subtract(couponPrice);
        //可用于优惠券数量-availableCouponLength
        List<Coupon> coupons = cartService.coupons(actualPrice);

        Map data = new HashMap();
        data.put("actualPrice",actualPrice);  //订单总价
        data.put("addressId",check.get("addressId"));  //收货地追id
        data.put("availableCouponLength",coupons.size());  //可用
        //data.put("checkedAddress",);
        data.put("checkedGoodsList",cartTotalVO.getCarts());
        data.put("couponId",check.get("couponId"));
        data.put("couponPrice",couponPrice);
        //data.put("freightPrice",);
        data.put("goodsTotalPrice",cartTotalVO.getCheckedGoodsAmount());
        data.put("grouponPrice",0);
        data.put("grouponRulesId",check.get("grouponRulesId"));
        data.put("orderTotalPrice",actualPrice);
        ResponseVO vo = new ResponseVO();
        vo.setData(data);
        vo.setErrmsg("成功");
        return vo;
    }*/

}