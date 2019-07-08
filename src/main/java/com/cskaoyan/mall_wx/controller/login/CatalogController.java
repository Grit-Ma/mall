package com.cskaoyan.mall_wx.controller.login;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.xfor.CatalogList;
import com.cskaoyan.bean.wx.xfor.CurrentCatalogList;
import com.cskaoyan.mall_wx.service.login.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("wx/catalog")
public class CatalogController {
    @Autowired
    CatalogService catalogService;

    @GetMapping("index")
    @ResponseBody
    public ResponseVO catalogIndex() {
        CatalogList catalogList = catalogService.getCatalogList();
        ResponseVO<CatalogList> catalogListResponseVO = new ResponseVO<>(200, catalogList, "ok");
        return catalogListResponseVO;
    }

    @GetMapping("current")
    @ResponseBody
    public ResponseVO catalogCurrent(@NotNull String id) {
        CurrentCatalogList currentCatalogList = catalogService.getCurrentCatalogList(Integer.valueOf(id));
        ResponseVO<CurrentCatalogList> currentCatalogListResponseVO = new ResponseVO<>(200, currentCatalogList, "ok");
        return currentCatalogListResponseVO;
    }
}
