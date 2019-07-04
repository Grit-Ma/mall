package com.cskaoyan.service.user.impl;

import com.cskaoyan.bean.User;
import com.cskaoyan.bean.UserExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.service.user.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public PageData getUser(int page, int limit, String username, String mobile, String sort, String order) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if ("".equals(username) && "".equals(mobile)) {
        } else if (!("".equals(username)) && !("".equals(mobile))) {
            criteria.andUsernameLike("%" + username + "%");
            criteria.andMobileLike("%" + mobile + "%");
        } else if ("".equals(username)) {
            criteria.andMobileLike("%" + mobile + "%");

        } else if ("".equals(mobile)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        long l = userMapper.countByExample(userExample);
        PageHelper.startPage(page, limit, sort + " " + order);
        List<User> userList = userMapper.selectByExample(userExample);
        return new PageData(userList, l);
    }

}
