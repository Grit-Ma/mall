package com.cskaoyan.mall_admin.service.promotion.Impl;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.Coupon.Column;
import com.cskaoyan.bean.CouponExample;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.bean.CouponUserExample;
import com.cskaoyan.bean.coupon.CouponConstant;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mapper.CouponMapper;
import com.cskaoyan.mapper.CouponUserMapper;

import com.cskaoyan.mall_admin.service.promotion.CouponService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/4
 */

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponUserMapper couponUserMapper;

    private Column[] result = new Column[]{Column.id, Column.name, Column.desc, Column.tag,
            Column.days, Column.startTime, Column.endTime,
            Column.discount, Column.min};

    @Override
    public PageData getCouponListData(int page, int limit, String sort, String order, String name, Short type, Short status) {
        PageHelper.startPage(page,limit);
        CouponExample example = new CouponExample();
        CouponExample.Criteria criteria = example.createCriteria();
        if(name != null){
            criteria.andNameLike("%"+name+"%");
        }
        if(type != null){
            criteria.andTypeEqualTo(type);
        }
        if(status != null){
            criteria.andStatusEqualTo(status);
        }
        example.setOrderByClause(sort+" "+order);
        List<Coupon> coupons = couponMapper.selectByExample(example);
        PageInfo<Coupon> pageinfo = new PageInfo(coupons);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public ResponseVO updateCoupon(Coupon coupon) {
        ResponseVO vo = new ResponseVO();
        try{
            couponMapper.updateByPrimaryKey(coupon);
            vo.setErrmsg("修改成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("修改失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public ResponseVO deleteCoupon(Coupon coupon) {
        ResponseVO vo = new ResponseVO();
        try{
            couponMapper.deleteByPrimaryKey(coupon.getId());
            vo.setErrmsg("删除成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("删除失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public ResponseVO insertCoupon(Coupon coupon) {
        ResponseVO vo = new ResponseVO();
        try{
            couponMapper.insert(coupon);
            vo.setErrmsg("添加成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("添加失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public Coupon searchCouponById(int id){
        return couponMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageData getCouponUserList(int page, int limit, String sort, String order,Integer userId, Integer couponId, Short status) {
        PageHelper.startPage(page,limit);
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        criteria.andCouponIdEqualTo(couponId);
        if(userId != null){
            criteria.andIdEqualTo(userId);
        }
        if(status != null){
            criteria.andStatusEqualTo(status);
        }
        example.setOrderByClause(sort+" "+order);
        List<CouponUser> coupons = couponUserMapper.selectByExample(example);
        PageInfo<CouponUser> pageinfo = new PageInfo(coupons);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public List<Coupon> queryExpired() {
        CouponExample example = new CouponExample();
        example.or().andStatusEqualTo(CouponConstant.STATUS_NORMAL).andTimeTypeEqualTo(CouponConstant.TIME_TYPE_TIME).andEndTimeLessThan(new Date()).andDeletedEqualTo(false);
        return couponMapper.selectByExample(example);
    }
}
