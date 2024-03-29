package com.cskaoyan.mall_admin.controller.user;

import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.user.FootprintService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("admin/footprint")
public class FootprintController {

    @Autowired
    FootprintService userService;

    //会员足迹List+模糊查询
    @RequestMapping("list")
    @ResponseBody
    @RequiresPermissions(value = "admin:footprint:list")
    public ResponseVO userFootprint(int page, int limit, String sort, String order, Integer userId, Integer goodsId){
        ResponseVO responseVO = new ResponseVO();
        if (userId==null && goodsId==null) {
            PageData data = userService.footprintList(page, limit, sort, order);
            responseVO.setErrmsg("成功");
            responseVO.setData(data);
        }else {

            PageData data = userService.footprintSearch(page, limit, sort, order, userId, goodsId);
            responseVO.setErrmsg("成功");
            responseVO.setData(data);
        }

        return responseVO;
    }



}
