package com.cskaoyan.controller.mallManage;

import com.cskaoyan.bean.mallmanage.region.Province;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.mallManageService.RegionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RegionController {
    @Autowired
    RegionService regionService;
    //admin/region/list
   @RequestMapping("region/list")
    @ResponseBody
   @RequiresPermissions(value = "admin:region:list")
    public ResponseVO regionList(){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        List<Province> list = regionService.regionList();
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(list);
        return responseVO;
    }

}
