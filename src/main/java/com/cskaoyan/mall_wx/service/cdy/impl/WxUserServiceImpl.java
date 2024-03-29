package com.cskaoyan.mall_wx.service.cdy.impl;

import com.cskaoyan.bean.User;
import com.cskaoyan.bean.UserExample;
import com.cskaoyan.mall_wx.service.cdy.WxUserService;
import com.cskaoyan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> queryByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(username).andDeletedEqualTo(false);

        return userMapper.selectByExample(userExample);
    }

    @Override
    public int updateById(User user) {
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(user.getUsername()).andDeletedEqualTo(false);

        return userMapper.updateByExampleSelective(user, userExample);
    }

    @Override
    public List<User> queryByMobile(String mobile) {
        UserExample userExample = new UserExample();
        userExample.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);

        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    @Override
    public int add(User user) {
        int insert = userMapper.insert(user);
        return insert;
    }
}
