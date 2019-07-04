package com.cskaoyan.service.user.impl;

import com.cskaoyan.bean.Footprint;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mapper.FootprintMapper;
import com.cskaoyan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    FootprintMapper footprintMapper;

    //会员足迹(footprint)list
    @Override
    public ResponseVO footprintList() {
        Map data = new HashMap();
        ResponseVO responseVO = new ResponseVO(0,data,"成功");

        List<Footprint> footprintList = footprintMapper.

        return responseVO;
    }
}
