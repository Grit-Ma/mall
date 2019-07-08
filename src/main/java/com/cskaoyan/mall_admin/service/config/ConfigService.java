package com.cskaoyan.mall_admin.service.config;
;
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
