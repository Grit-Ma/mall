package com.cskaoyan.promotion;

import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.AdExample;
import com.cskaoyan.mapper.AdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/3
 */

@Service
public class PromotionServiceImpl implements PromotionService{

    @Autowired
    AdMapper adMapper;

    @Override
    public List<Ad> findAllAds(){
        AdExample example = new AdExample();
        example.or().andPositionEqualTo((byte) 1).andDeletedEqualTo(false).andEnabledEqualTo(true);
        return adMapper.selectByExample(example);
    }
}
