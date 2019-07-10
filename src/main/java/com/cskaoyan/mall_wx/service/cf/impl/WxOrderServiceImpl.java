package com.cskaoyan.mall_wx.service.cf.impl;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.System;
import com.cskaoyan.bean.wx.order.*;
import com.cskaoyan.bean.wx.pagedata.OrderListPageData;
import com.cskaoyan.mall_admin.service.promotion.CouponService;
import com.cskaoyan.mall_wx.service.CartService;
import com.cskaoyan.mall_wx.service.cf.WxOrderService;
import com.cskaoyan.mall_wx.util.CharUtil;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import com.cskaoyan.mapper.*;
import com.cskaoyan.mapper.config.SystemMapper;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.cskaoyan.bean.coupon.CouponUserConstant.STATUS_USED;
import static com.cskaoyan.mall_wx.util.WxResponseCode.*;

@Service
public class WxOrderServiceImpl implements WxOrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    SystemMapper systemMapper;
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    CouponUserMapper couponUserMapper;
    @Autowired
    CouponService couponService;
    @Autowired
    CartService cartService;

    //这里开启了事务
    @Override
    public HashMap submitOrder(HttpServletRequest request, SubmitInfo submitInfo) {

        //判user信息空
        String tokenKey = request.getHeader("X-Litemall-Token");
        if (tokenKey == null || "".equals(tokenKey.trim()))
            return WrapTool.unlogin();
        //获取userId
        Integer userId = UserTokenManager.getUserId(tokenKey);

        //获取参数为空
        if (submitInfo == null) return WrapTool.setResponseFailure(401, "参数不对");
        int addressId, couponId, groupLinkId, grouponRulesId;
        String message;
        try {
            addressId = submitInfo.getAddressId().intValue();
            couponId = submitInfo.getCouponId().intValue();
            groupLinkId = submitInfo.getGrouponLinkId().intValue();
            grouponRulesId = submitInfo.getGrouponRulesId().intValue();
            message = submitInfo.getMessage();
        } catch (Exception e) {
            return WrapTool.setResponseFailure(401, "参数不对");
        }

        //检验购物车
        List<Cart> carts = cartService.getCheckedCartGood(userId);
        if (carts == null) return WrapTool.setResponseFailure(401, "参数不对");


        //检验收货地址
        Address address = addressMapper.selectByPrimaryKey(addressId);
        if (address == null) return WrapTool.setResponseFailure(401, "参数不对");
        String address1 = address.getAddress();
        if (address1 == null) return WrapTool.setResponseFailure(401, "参数不对");


        //检验团购优惠,方便计算商品总价格；团购优惠价减免
        BigDecimal groupDiscount = new BigDecimal(0.00);
        Integer grouponGoodId = -1;
        GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(grouponRulesId);
        if (grouponRulesId > 0) {
            if (grouponRules.getExpireTime().getTime() < new Date().getTime())
                return WrapTool.setResponseFailure(GROUPON_EXPIRED, "团购优惠已过期");
            groupDiscount = grouponRules.getDiscount();
            grouponGoodId = grouponRules.getGoodsId();
        }

        //计算每个商品的价格之和；注意团购优惠只能对指定的商品生效;goods_price商品总费用(已经自动减去团购价减免)
        BigDecimal goodsPrice = new BigDecimal(0.00);
        for (Cart good : carts) {
            if (good.getGoodsId() == grouponGoodId) {
                goodsPrice = goodsPrice.add(good.getPrice().subtract(groupDiscount).multiply(new BigDecimal(good.getNumber())));
            }
            goodsPrice = goodsPrice.add(good.getPrice().multiply(new BigDecimal(good.getNumber())));
        }


        //检验优惠券,优惠券减免;这里参数有限，后期如果有更多规则，需要增添逻辑
        BigDecimal couponDiscount = new BigDecimal(0.00);
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if (couponId > 0 && coupon.getDeleted() == false) {
            //判断优惠券是否过期

//            coupon = couponService.checkCouponValid(userId, couponId, goodsPrice);
            couponDiscount = coupon.getDiscount();
        }


        //计算邮费,(goodsPrice)满设定值包邮
        SystemExample e = new SystemExample();
        e.createCriteria().andKeyNameEqualTo("cskaoyan_mall_express_freight_min");
        List<System> systems = systemMapper.selectByExample(e);
        BigDecimal freightPrice = new BigDecimal(0.00);
        if (goodsPrice.compareTo(new BigDecimal(systems.get(0).getKeyValue())) < 0) {
            e = new SystemExample();
            e.createCriteria().andKeyNameEqualTo("cskaoyan_mall_express_freight_value");
            freightPrice = new BigDecimal(systemMapper.selectByExample(e).get(0).getKeyValue());
        }

        //订单费用， orderPrice = goods_price + freight_price - coupon_price
        BigDecimal orderPrice = new BigDecimal(0.00);
        orderPrice = goodsPrice.add(freightPrice).subtract(couponDiscount);

        // 用户积分减免
        BigDecimal integralPrice = new BigDecimal(0.00);

        // 实付费用，actual_price = order_price - integral_price
        BigDecimal actual_price = new BigDecimal(0.00);
        actual_price = orderPrice.subtract(integralPrice);


        //添加订单表里的一条信息，回显id
        //这里创建订单默认是未支付，状态码101
        short s = 101, s1 = 0;
        String addressInfo = regionMapper.selectByPrimaryKey(address.getProvinceId()).getName() +
                regionMapper.selectByPrimaryKey(address.getCityId()).getName() +
                regionMapper.selectByPrimaryKey(address.getAreaId()).getName() +
                address.getAddress();
        Order order = new Order(null, userId, getOrderSn(userId), new Short(s), address.getName(), address.getMobile(),
                addressInfo, message, goodsPrice, freightPrice,
                couponDiscount, integralPrice, groupDiscount, orderPrice,
                actual_price, null, null, null, null, null, null, s1, null, new Date(), new Date(), false);
        orderMapper.insert(order);
        int orderId = order.getId();

        //添加order_goods表
        for (Cart orderGood : carts) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(orderId);
            orderGoods.setGoodsId(orderGood.getGoodsId());
            orderGoods.setGoodsSn(orderGood.getGoodsSn());
            orderGoods.setProductId(orderGood.getProductId());
            orderGoods.setGoodsName(orderGood.getGoodsName());
            orderGoods.setPicUrl(orderGood.getPicUrl());
            orderGoods.setPrice(orderGood.getPrice());
            orderGoods.setNumber(orderGood.getNumber());
            orderGoods.setSpecifications("" + orderGood.getSpecifications().toString());
            orderGoods.setAddTime(new Date());
            orderGoods.setUpdateTime(new Date());
            orderGoods.setDeleted(false);
            orderGoodsMapper.insert(orderGoods);
        }

        //清空购物车操作（如果购物车里面userid对应条目为空，清空购物车）；这里提醒马致远修改。。。deleted ，新增一条
        cartService.clearCart(userId);

        //商品数量减少
        // 商品货品数量减少
        for (Cart good : carts) {
            Integer productId = good.getProductId();
            GoodsProduct product = goodsProductMapper.selectByPrimaryKey(productId);

            Integer remainNumber = product.getNumber() - good.getNumber();
            if (remainNumber < 0) {
                throw new RuntimeException("下单的商品" + good.getGoodsName() + "库存量不足");
            }
            if (product.getNumber() - good.getNumber() < 0) {
                throw new RuntimeException("商品货品" + good.getGoodsName() + "库存减少失败");
            }
            GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(productId);
            goodsProduct.setNumber(remainNumber);
            goodsProductMapper.updateByPrimaryKey(goodsProduct);
        }

        //如果使用了团购，添加团购信息表


        //如果使用了优惠券，更新优惠券用户列表
        CouponUserExample couponUserExample = new CouponUserExample();
        couponUserExample.createCriteria().andUserIdEqualTo(userId).andCouponIdEqualTo(couponId);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        if (couponUsers != null && !(couponUsers.size() == 0)) {
            CouponUser couponUser = couponUsers.get(0);
            couponUser.setOrderId(orderId);
            couponUser.setUsedTime(new Date());
            couponUser.setStatus(STATUS_USED);
            couponUserMapper.updateByPrimaryKeySelective(couponUser);
        }

        return WrapTool.setResponseSuccess(orderId);
    }


    @Override
    public HashMap showOrderList(int showTpe, int page, int size, String sort, String order) {
        sort = sort.trim();
        order = order.trim();
        switch (showTpe) {
            case 0:
                return WrapTool.setResponseSuccess(showOrdersByStatus((short) 1, page, size, sort, order));
            case 1:
                return WrapTool.setResponseSuccess(showOrdersByStatus((short) 101, page, size, sort, order));
            case 2:
                return WrapTool.setResponseSuccess(showOrdersByStatus((short) 201, page, size, sort, order));
            case 3:
                return WrapTool.setResponseSuccess(showOrdersByStatus((short) 301, page, size, sort, order));
            case 4:
                return WrapTool.setResponseSuccess(showOrdersByStatus((short) 401, page, size, sort, order));
        }
        return WrapTool.setResponseSuccess(new OrderListPageData());
    }


    //封装订单页面信息
    private OrderListPageData showOrdersByStatus(short orderStatus, int page, int size, String sort, String order) {
        List<WxOrder> orders = orderMapper.showOrdersByStatus(orderStatus, sort, order);
        for (WxOrder o : orders) {
            o.setOrderStatusText(getStatusText(o.getOrder_status()));
            o.setGoodList(getGoodList(o.getId()));
            o.setHandleOption(build(o.getOrder_status().intValue()));
        }
        return WrapTool.setOrderListPageData(page, size, orders);
    }

    //根据订单id获取订单商品信息
    private List<OrderGoods> getGoodList(Integer oid) {
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(oid).andDeletedEqualTo(false);
        List<OrderGoods> goods = orderGoodsMapper.selectByExample(orderGoodsExample);
        return goods;
    }

    private String getStatusText(short orderStatus) {
        switch (orderStatus) {
            case 101:
                return "未付款";
            case 201:
                return "未发货";
            case 301:
                return "待收货";
            case 401:
                return "待评价";
            case 102:
                return "已取消（用户）";
            case 103:
                return "已取消（系统）";
            case 402:
                return "已收货";
            default:
                return "未知状态";
        }
    }

    //获取订单中每件商品的可操作信息
    public static HandleOption build(int status) {
        HandleOption handleOption = new HandleOption();

        if (status == 101) {
            // 如果订单没有被取消，且没有支付，则可支付，可取消
            handleOption.setCancel(true);
            handleOption.setPay(true);
        } else if (status == 102 || status == 103) {
            // 如果订单已经取消或是已完成，则可删除
            handleOption.setDelete(true);
        } else if (status == 201) {
            // 如果订单已付款，没有发货，则可退款
            handleOption.setRefund(true);
        } else if (status == 202) {
            // 如果订单申请退款中，没有相关操作
        } else if (status == 203) {
            // 如果订单已经退款，则可删除
            handleOption.setDelete(true);
        } else if (status == 301) {
            // 如果订单已经发货，没有收货，则可收货操作,
            // 此时不能取消订单
            handleOption.setConfirm(true);
        } else if (status == 401 || status == 402) {
            // 如果订单已经支付，且已经收货，则可删除、去评论和再次购买
            handleOption.setDelete(true);
            handleOption.setComment(true);
            handleOption.setRebuy(true);
        } else {
            throw new IllegalStateException("status不支持");
        }
        return handleOption;
    }

    //获取订单编码 时间+随机数
    public String getOrderSn(int userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + CharUtil.getRandomNum(6);
        while (countByOrderSn(userId, orderSn) != 0) {
            orderSn = now + CharUtil.getRandomNum(6);
        }
        return orderSn;
    }

    //判断订单编码没有重复
    public int countByOrderSn(Integer userId, String orderSn) {
        OrderExample example = new OrderExample();
        example.or().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return (int) orderMapper.countByExample(example);
    }


    //获取订单详情
    @Override
    public HashMap detail(HttpServletRequest request, Integer orderId) {
        //判user信息空
        String tokenKey = request.getHeader("X-Litemall-Token");
        if (tokenKey == null || "".equals(tokenKey.trim()))
            return WrapTool.unlogin();
        //获取userId
        Integer userId = UserTokenManager.getUserId(tokenKey);


        // 订单信息
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (null == order) {
            return WrapTool.setResponseFailure(ORDER_UNKNOWN, "订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            return WrapTool.setResponseFailure(ORDER_INVALID, "不是当前用户的订单");
        }
        Map<String, Object> orderInfo = new HashMap<String, Object>();
        orderInfo.put("id", order.getId());
        orderInfo.put("orderSn", order.getOrderSn());
        orderInfo.put("addTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getAddTime()));
        orderInfo.put("consignee", order.getConsignee());
        orderInfo.put("mobile", order.getMobile());
        orderInfo.put("address", order.getAddress());
        orderInfo.put("goodsPrice", order.getGoodsPrice());
        orderInfo.put("couponPrice", order.getCouponPrice());
        orderInfo.put("freightPrice", order.getFreightPrice());
        orderInfo.put("actualPrice", order.getActualPrice());
        orderInfo.put("orderStatusText", getStatusText(order.getOrderStatus()));
        orderInfo.put("handleOption", build(order.getOrderStatus()));
        orderInfo.put("expCode", order.getShipChannel());
        orderInfo.put("expNo", order.getShipSn());


        Map<String, Object> result = new HashMap<>();
        result.put("orderInfo", orderInfo);
        result.put("orderGoods", getGoodList(orderId));

        //物流信息？？？？

        return WrapTool.setResponseSuccess(result);

    }

    //取消订单
    @Override
    public HashMap cancelOrder(HttpServletRequest request, SubmitResponse submitResponse) {

        //判user信息空
        String tokenKey = request.getHeader("X-Litemall-Token");
        if (tokenKey == null || "".equals(tokenKey.trim()))
            return WrapTool.unlogin();
        //获取userId
        Integer userId = UserTokenManager.getUserId(tokenKey);


        if(submitResponse==null)return WrapTool.setResponseFailure(401,"参数错误");
        if(submitResponse.getOrderId()==null)return WrapTool.setResponseFailure(401,"参数错误");

        int orderId = submitResponse.getOrderId();
        int status= orderMapper.selectByPrimaryKey(orderId).getOrderStatus().intValue();
        HandleOption handleOption = build(status);
        if(handleOption.getCancel())
            cancelThisOrder(orderId);
        return null;
    }

    @Transactional
    protected void cancelThisOrder(int orderId) {
        //改变订单表状态
        Order order = orderMapper.selectByPrimaryKey(orderId);
        short s=102;
        order.setOrderStatus(s);
        orderMapper.updateByPrimaryKey(order);

        //

    }


    //获取订单的几个不同状态的计数（用在首页）
    @Override
    public Map<String, Integer> orderInfo(Integer userId) {
        OrderExample orderExample = new OrderExample();
        orderExample.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        int unpaid = 0;
        int unship = 0;
        int unrecv = 0;
        int uncomment = 0;
        if (null == orders) {
            return null;
        } else {
            Map<String, Integer> map = new HashMap<>();
            for (Order order : orders) {
                if (order.getOrderStatus() == 101) {
                    unpaid++;
                } else if (order.getOrderStatus() == 201) {
                    unship++;
                } else if (order.getOrderStatus() == 301) {
                    unrecv++;
                } else if (order.getOrderStatus() == 401) {
                    uncomment++;
                }
            }
            map.put("unpaid", unpaid);
            map.put("unship", unship);
            map.put("unrecv", unrecv);
            map.put("uncomment", uncomment);

            return map;
        }
    }

}
