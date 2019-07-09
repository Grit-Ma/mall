package com.cskaoyan.bean.vo;

import com.cskaoyan.bean.Cart;

import java.util.List;

public class CartTotalVO {

    long checkedGoodsAmount;

    long checkedGoodsCount;

    long goodsAmount;

    long goodsCount;

    List<Cart> carts;

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public CartTotalVO() {
    }


    public CartTotalVO(long checkedGoodsAmount, long checkedGoodsCount, long goodsAmount, long goodsCount) {
        this.checkedGoodsAmount = checkedGoodsAmount;
        this.checkedGoodsCount = checkedGoodsCount;
        this.goodsAmount = goodsAmount;
        this.goodsCount = goodsCount;
    }


    @Override
    public String toString() {
        return "CartTotalVO{" +
                "checkedGoodsAmount=" + checkedGoodsAmount +
                ", checkedGoodsCount=" + checkedGoodsCount +
                ", goodsAmount=" + goodsAmount +
                ", goodsCount=" + goodsCount +
                '}';
    }

    public double getCheckedGoodsAmount() {
        return checkedGoodsAmount;
    }

    public void setCheckedGoodsAmount(long checkedGoodsAmount) {
        this.checkedGoodsAmount = checkedGoodsAmount;
    }

    public long getCheckedGoodsCount() {
        return checkedGoodsCount;
    }

    public void setCheckedGoodsCount(long checkedGoodsCount) {
        this.checkedGoodsCount = checkedGoodsCount;
    }

    public double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(long goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public long getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(long goodsCount) {
        this.goodsCount = goodsCount;
    }
}
