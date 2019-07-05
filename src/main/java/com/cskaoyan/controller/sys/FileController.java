package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.oss.MyOssClient;
import com.cskaoyan.bean.sys.Storage;
import com.cskaoyan.bean.sys.UpStorage;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.sys.StorageService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class FileController {

    @Autowired
    MyOssClient myOssClient;
    @Autowired
    StorageService storageService;


    @RequestMapping("storage/create")
    @ResponseBody
    public HashMap fileUpload(MultipartFile file) throws IOException {
        Storage storage = myOssClient.fileUpLoad(file);
        int i =storageService.insertStorage(storage);
        if(i==1){
            return WrapTool.setResponseSuccess(new UpStorage(storage.getId(),storage.getKey(),storage.getName(),storage.getType(),storage.getSize(),storage.getUrl(),storage.getAddTime(),storage.getUpdateTime()));
        }else {
            return  WrapTool.setResponseFailure(1,"上传添加失败！");
        }
    }

}
