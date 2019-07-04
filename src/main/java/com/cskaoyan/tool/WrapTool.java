package com.cskaoyan.tool;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class WrapTool {
    public static ResponseVO setResponseSuccess(Object pageData){
        ResponseVO pageDataResponseVO = new ResponseVO<>();
        pageDataResponseVO.setData(pageData);
        pageDataResponseVO.setErrmsg("成功");
        pageDataResponseVO.setErrno(0);
        return pageDataResponseVO;
    }

    public static PageData setPageData(int page, int limit,List list){
        PageHelper.startPage(page,limit);
        PageInfo<Admin> pageInfo = new PageInfo<>(list);
        PageData pageData = new PageData();
        pageData.setItems(pageInfo.getList());
        pageData.setTotal(list.size());
        return pageData;
    }

    public static ResponseVO setResponseFailure(int errno,String errmsg){
        ResponseVO pageDataResponseVO = new ResponseVO<>();
        pageDataResponseVO.setErrmsg(errmsg);
        pageDataResponseVO.setErrno(errno);
        return pageDataResponseVO;
    }



}
