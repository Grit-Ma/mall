package com.cskaoyan.mall_admin.service.stat;

import java.util.List;
import java.util.Map;

public interface StatService {
    List<Map> statUser();

    List<Map> statOrder();

    List<Map> statGoods();
}
