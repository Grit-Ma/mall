package com.cskaoyan.mapper.stat;

import java.util.List;
import java.util.Map;

public interface StatMapper {
    public List<Map> statUser();

    List<Map> statOrder();

    List<Map> statGoods();
}
