package com.cskaoyan.bean;

import java.util.Arrays;

/**
 * created by ZengWei
 * on 2019/7/6
 */
public class GroupActivity {

    private Goods goods;

    private Groupon groupon;

    private GrouponRules rules;

    private Integer[] subGroupons;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Groupon getGroupon() {
        return groupon;
    }

    public void setGroupon(Groupon groupon) {
        this.groupon = groupon;
    }

    public GrouponRules getRules() {
        return rules;
    }

    public void setRules(GrouponRules rules) {
        this.rules = rules;
    }

    public Integer[] getSubGroupons() {
        return subGroupons;
    }

    public void setSubGroupons(Integer[] subGroupons) {
        this.subGroupons = subGroupons;
    }

    @Override
    public String toString() {
        return "GroupActivity{" +
                "goods=" + goods +
                ", groupon=" + groupon +
                ", rules=" + rules +
                ", subGroupons=" + Arrays.toString(subGroupons) +
                '}';
    }
}