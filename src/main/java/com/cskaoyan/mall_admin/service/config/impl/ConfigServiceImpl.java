package com.cskaoyan.mall_admin.service.config.impl;

import com.cskaoyan.bean.System;
import com.cskaoyan.bean.SystemExample;
import com.cskaoyan.mapper.config.SystemMapper;
import com.cskaoyan.mall_admin.service.config.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private SystemMapper systemMapper;

    @Override
    public Map<String, Object> queryAll() {
        SystemExample example = new SystemExample();
        example.or().andDeletedEqualTo(false);
        List<System> systemList = systemMapper.selectByExample(example);
        return getDate(systemList);
    }

    public Map<String, Object> getDate(List<System> systemList) {
        Map<String, Object> data = new HashMap<>();
        for (System item : systemList) {
            data.put(item.getKeyName(), item.getKeyValue());
        }
        return data;
    }

    @Override
    public Map<String, Object> listMall() {
        SystemExample example = new SystemExample();
        example.or().andKeyNameLike("cskaoyan_mall_mall_%").andDeletedEqualTo(false);
        List<System> systemList = systemMapper.selectByExample(example);
        return getDate(systemList);
    }

    @Override
    public Map<String, Object> listWx() {
        SystemExample example = new SystemExample();
        example.or().andKeyNameLike("cskaoyan_mall_wx_%").andDeletedEqualTo(false);
        List<System> systemList = systemMapper.selectByExample(example);
        return getDate(systemList);
    }

    @Override
    public Map<String, Object> listOrder() {
        SystemExample example = new SystemExample();
        example.or().andKeyNameLike("cskaoyan_mall_order_%").andDeletedEqualTo(false);
        List<System> systemList = systemMapper.selectByExample(example);
        return getDate(systemList);
    }

    @Override
    public Map<String, Object> listExpress() {
        SystemExample example = new SystemExample();
        example.or().andKeyNameLike("cskaoyan_mall_express_%").andDeletedEqualTo(false);
        List<System> systemList = systemMapper.selectByExample(example);
        return getDate(systemList);
    }

    @Override
    public void updateConfig(Map<String, Object> data) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            SystemExample example = new SystemExample();
            example.or().andKeyNameEqualTo(entry.getKey()).andDeletedEqualTo(false);

            System system = new System();
            system.setKeyName(entry.getKey());
            system.setKeyValue(entry.getValue().toString());
            system.setUpdateTime(new Date());
            systemMapper.updateByExampleSelective(system, example);
        }

    }

    @Override
    public void addConfig(String key, String value) {
        System system = new System();
        system.setKeyName(key);
        system.setKeyValue(value);
        system.setAddTime(new Date());
        system.setUpdateTime(new Date());
        systemMapper.insertSelective(system);
    }

}
