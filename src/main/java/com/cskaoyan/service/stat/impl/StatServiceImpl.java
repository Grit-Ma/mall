package com.cskaoyan.service.stat.impl;

import com.cskaoyan.mapper.stat.StatMapper;
import com.cskaoyan.service.stat.StatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {

    @Resource
    private StatMapper statMapper;

    @Override
    public List<Map> statUser() {
        return statMapper.statUser();
    }

    @Override
    public List<Map> statOrder() {
        return statMapper.statOrder();
    }

    @Override
    public List<Map> statGoods() {
        return statMapper.statGoods();
    }
}
