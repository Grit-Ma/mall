package com.cskaoyan.mall_admin.controller.sys;

import com.cskaoyan.bean.oss.MyOssClient;
import com.cskaoyan.bean.sys.Storage;
import com.cskaoyan.bean.sys.UpStorage;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mall_admin.service.sys.StorageService;
import com.cskaoyan.tool.WrapTool;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    MyOssClient myOssClient;
    @Autowired
    StorageService storageService;



    @RequiresPermissions(value = "admin:storage:create")
    @PostMapping("storage/create")
    public HashMap fileUpload(MultipartFile file) throws IOException {
        Storage storage = myOssClient.fileUpLoad(file);
        int i = storageService.insertStorage(storage);
        if (i == 1) {
            return WrapTool.setResponseSuccess(new UpStorage(storage.getId(), storage.getKey(), storage.getName(), storage.getType(), storage.getSize(), storage.getUrl(), storage.getAddTime(), storage.getUpdateTime()));
        } else {
            return WrapTool.setResponseFailure(401, "参数不对");
        }
    }



    @RequiresPermissions(value = "admin:storage:list")
    @GetMapping("storage/list")
    public HashMap storeList(String key, String name, String sort, String order,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "limit", defaultValue = "20") int limit) {
        List<Storage> storages = storageService.fuzzyQuery(key, name, sort, order);
        PageData pageData = WrapTool.setPageData(page, limit, storages);
        return WrapTool.setResponseSuccess(pageData);
    }


    @RequiresPermissions(value = "admin:role:update")
    @PostMapping("storage/update")
    public HashMap updateStorage(@RequestBody Storage storage) {
        storage.setUpdateTime(new Date());
        int i = storageService.updateStorage(storage);
        if (i == 1) {
            return WrapTool.setResponseSuccess(storage);
        } else {
            return WrapTool.setResponseFailure(401, "参数不对");
        }
    }


    @RequiresPermissions(value = "admin:storage:delete")
    @PostMapping("storage/delete")
    public HashMap deleteStorage(@RequestBody Storage storage) {
        storage.setDeleted(true);
        int i = storageService.updateStorage(storage);
        if (i == 1) {
            return WrapTool.setResponseSuccessWithNoData();
        } else {
            return WrapTool.setResponseFailure(401, "参数不对");
        }
    }

}
