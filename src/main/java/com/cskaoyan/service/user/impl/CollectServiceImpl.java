package com.cskaoyan.service.user.impl;

import com.cskaoyan.bean.Collect;
import com.cskaoyan.bean.CollectExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.CollectMapper;
import com.cskaoyan.service.user.CollectService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    CollectMapper collectMapper;

    @Override
    public PageData getCollect(int page, int limit, String userId, String valueId, String sort, String order) {
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        if ("".equals(userId) && "".equals(valueId)) {
        } else if (!("".equals(userId)) && !("".equals(valueId))) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        } else if ("".equals(userId)) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        } else if ("".equals(valueId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        long l = collectMapper.countByExample(collectExample);
        PageHelper.startPage(page, limit, sort + " " + order);
        List<Collect> collectList = collectMapper.selectByExample(collectExample);
        return new PageData(collectList, l);
    }
}
