package com.cskaoyan.mall_wx.controller.cdy;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.WxGroupon;
import com.cskaoyan.bean.wx.WxListVo;
import com.cskaoyan.mall_wx.service.cdy.WxGrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wx/groupon")
public class WxGrouponController {

    @Autowired
    WxGrouponService wxGrouponService;

    @GetMapping("list")
    public ResponseVO list(int page,int size) {
        ResponseVO vo = new ResponseVO();
        WxListVo  wxListVo = wxGrouponService.list(page, size);
        vo.setData(wxListVo);
        return vo.ok(vo);
    }

    @GetMapping("my")
    public ResponseVO my() {
        return null;
    }

}
