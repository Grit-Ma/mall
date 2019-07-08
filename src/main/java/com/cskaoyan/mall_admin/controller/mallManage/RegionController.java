package com.cskaoyan.mall_admin.controller.mallManage;

import com.cskaoyan.bean.mallmanage.region.Province;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.mallManageService.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin")
public class RegionController {
    @Autowired
    RegionService regionService;
    //admin/region/list
   @RequestMapping("region/list")
    @ResponseBody
    public ResponseVO regionList(){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        List<Province> list = regionService.regionList();
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(list);
        return responseVO;
    }

}
