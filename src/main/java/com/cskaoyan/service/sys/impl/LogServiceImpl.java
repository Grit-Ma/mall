package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.Log;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.sys.LogMapper;
import com.cskaoyan.service.sys.LogService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogMapper logMapper;


    @Override
    public PageData fuzzyQueryByName(int page, int limit, String name, String sort, String order) {
        List<Log> logs = logMapper.fuzzyQueryByName(name,sort,order);
        return WrapTool.setPageData(page,limit,logs);
    }
}
