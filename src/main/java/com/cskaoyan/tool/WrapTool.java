package com.cskaoyan.tool;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

public class WrapTool {
    public static HashMap setResponseSuccess(Object pageData){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data",pageData);
        map.put("errmsg","成功");
        map.put("errno",0);
        return map;
    }

    public static PageData setPageData(int page, int limit,List list){
        PageHelper.startPage(page,limit);
        PageInfo<Admin> pageInfo = new PageInfo<>(list);
        PageData pageData = new PageData();
        pageData.setItems(pageInfo.getList());
        pageData.setTotal(list.size());
        return pageData;
    }

    public static HashMap setResponseSuccessWithNoData(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("errmsg","成功");
        map.put("errno",0);
        return map;
    }
    public static HashMap setResponseFailure(int errno,String errmsg){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("errmsg",errmsg);
        map.put("errno",errno);
        return map;
    }



}
