package com.cskaoyan.service.config;

import com.cskaoyan.bean.System;

import java.util.List;
import java.util.Map;

public interface ConfigService {
    Map<String, Object> queryAll();

    Map<String, Object> listMall();

    Map<String, Object> listWx();

    Map<String, Object> listOrder();

    Map<String, Object> listExpress();

    void updateConfig(Map<String, Object> data);

    void addConfig(String key, String value);
}
