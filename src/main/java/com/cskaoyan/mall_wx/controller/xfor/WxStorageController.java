package com.cskaoyan.mall_wx.controller.xfor;

import com.cskaoyan.bean.sys.Storage;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.sys.StorageService;
import com.cskaoyan.mall_wx.service.xfor.WxStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("wx/storage")
public class WxStorageController {
    @Autowired
    WxStorageService wxStorageService;

    @PostMapping("upload")
    @ResponseBody
    public ResponseVO upload(@RequestParam("file")MultipartFile file) {
        Storage store = wxStorageService.store(file);
        ResponseVO<Storage> storageResponseVO = new ResponseVO<>(0, store, "成功");
        return storageResponseVO;
    }
}
