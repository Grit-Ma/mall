package com.cskaoyan.mall_wx.controller.xfor;

import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.mallManageService.RegionService;
import com.cskaoyan.mall_wx.service.xfor.WxRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("wx/region")
public class WxRegionController {
    @Autowired
    WxRegionService wxRegionService;

    @GetMapping("list")
    @ResponseBody
    public ResponseVO getRegionList(@NotNull int pid) {
        List<Region> regionList = wxRegionService.getRegionList(pid);
        ResponseVO<List<Region>> regionListResponseVO = new ResponseVO<>(0, regionList, "成功");
        return regionListResponseVO;
    }
}
