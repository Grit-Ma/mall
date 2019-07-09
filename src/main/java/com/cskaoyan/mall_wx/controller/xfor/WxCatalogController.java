package com.cskaoyan.mall_wx.controller.xfor;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.xfor.CatalogList;
import com.cskaoyan.bean.wx.xfor.CurrentCatalogList;
import com.cskaoyan.mall_wx.service.xfor.WxCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("wx/catalog")
public class WxCatalogController {
    @Autowired
    WxCatalogService catalogService;

    @GetMapping("index")
    @ResponseBody
    public ResponseVO catalogIndex() {
        CatalogList catalogList = catalogService.getCatalogList();
        ResponseVO<CatalogList> catalogListResponseVO = new ResponseVO<>(0, catalogList, "成功");
        return catalogListResponseVO;
    }

    @GetMapping("current")
    @ResponseBody
    public ResponseVO catalogCurrent(@NotNull String id) {
        CurrentCatalogList currentCatalogList = catalogService.getCurrentCatalogList(Integer.valueOf(id));
        ResponseVO<CurrentCatalogList> currentCatalogListResponseVO = new ResponseVO<>(0, currentCatalogList, "成功");
        return currentCatalogListResponseVO;
    }
}
