package com.cskaoyan.service.promotion.Impl;


import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.AdExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mapper.AdMapper;
import com.cskaoyan.service.promotion.AdService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/3
 */

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    AdMapper adMapper;

    @Override
    public PageData getAdListData(int page, int limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        AdExample example = new AdExample();
        example.setOrderByClause(sort+" "+order);
        List<Ad> ads = adMapper.selectByExample(example);
        PageInfo<Ad> pageinfo = new PageInfo(ads);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public ResponseVO updateAd(Ad ad) {
        ResponseVO vo = new ResponseVO();
        try{
            adMapper.updateByPrimaryKey(ad);
            vo.setErrmsg("修改成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("修改失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public ResponseVO deleteAd(Ad ad) {
        ResponseVO vo = new ResponseVO();
        try{
            adMapper.deleteByPrimaryKey(ad.getId());
            vo.setErrmsg("修改成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("修改失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public PageData searchAdByName(int page, int limit, String sort, String order, String name) {
        PageHelper.startPage(page,limit);
        AdExample example = new AdExample();
        example.setOrderByClause(sort+" "+order);
        example.createCriteria().andNameLike(name);
        List<Ad> ads = adMapper.selectByExample(example);
        PageInfo<Ad> pageinfo = new PageInfo(ads);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public PageData searchAdByContent(int page, int limit, String sort, String order, String content) {
        PageHelper.startPage(page,limit);
        AdExample example = new AdExample();
        example.setOrderByClause(sort+" "+order);
        example.createCriteria().andContentLike(content);
        List<Ad> ads = adMapper.selectByExample(example);
        PageInfo<Ad> pageinfo = new PageInfo(ads);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public PageData searchAdByNameAndContent(int page, int limit, String sort, String order, String name, String content) {
        PageHelper.startPage(page,limit);
        AdExample example = new AdExample();
        example.setOrderByClause(sort+" "+order);
        example.createCriteria().andNameLike(name);
        example.createCriteria().andContentLike(content);
        List<Ad> ads = adMapper.selectByExample(example);
        PageInfo<Ad> pageinfo = new PageInfo(ads);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }
}
