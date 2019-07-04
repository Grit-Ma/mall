package com.cskaoyan.controller.sys;

import com.cskaoyan.bean.oss.MyOssClient;
import com.cskaoyan.bean.sys.Storage;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.sys.StorageService;
import com.cskaoyan.tool.WrapTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Controller
public class FileController {

    @Autowired
    MyOssClient myOssClient;
    @Autowired
    StorageService storageService;


    @RequestMapping("admin/storage/create")
    @ResponseBody
    public ResponseVO fileUpload(MultipartFile myfile) throws IOException {
        Storage storage = myOssClient.FileUpLoad(myfile);
        int i =storageService.insertStorage(storage);
        if(i==1){
            return WrapTool.setResponseSuccess(storage);
        }else {
            return  WrapTool.setResponseFailure(1,"上传添加失败！");
        }
    }

}
