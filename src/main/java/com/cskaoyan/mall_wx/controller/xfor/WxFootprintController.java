package com.cskaoyan.mall_wx.controller.xfor;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.xfor.FootprintList;
import com.cskaoyan.mall_wx.service.xfor.WxFootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("wx/footprint")
public class WxFootprintController {
    @Autowired
    WxFootprintService footprintService;

    @GetMapping("list")
    @ResponseBody
    public ResponseVO getFootprintList(int page, int size) {
        FootprintList footprintList = footprintService.getFootprintList(page, size);
        ResponseVO<FootprintList> footprintListResponseVO = new ResponseVO<>(0, footprintList, "成功");
        return footprintListResponseVO;
    }

    @GetMapping("delete")
    @ResponseBody
    public ResponseBody deleteFootprint() {
        return null;
    }
}
