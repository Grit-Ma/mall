package com.cskaoyan.mall_wx.service.xfor.impl;

import com.cskaoyan.bean.oss.MyOssClient;
import com.cskaoyan.bean.sys.Storage;
import com.cskaoyan.bean.sys.StorageExample;
import com.cskaoyan.config.XforOssClient;
import com.cskaoyan.converter.DateConverter;
import com.cskaoyan.mall_wx.service.xfor.WxStorageService;
import com.cskaoyan.mall_wx.util.CharUtil;
import com.cskaoyan.mapper.sys.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WxStorageServiceImpl implements WxStorageService {
    @Autowired
    StorageMapper storageMapper;

    @Autowired
    DateConverter dateConverter;

    @Autowired
    XforOssClient myOssClient;

    @Override
    public Storage store(MultipartFile file) {
        String uuid = null;
        try {
            uuid =  myOssClient.ossFileUpload(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String bucket = myOssClient.getBucket();
        String endPoint = myOssClient.getEndPoint();
        Storage storage = new Storage();
        String key = generateKey(file.getOriginalFilename());
        storage.setKey(key);
        storage.setName(uuid);
        storage.setType(file.getContentType());
        storage.setSize((int) file.getSize());
        storage.setUrl("https://" + bucket + "." + endPoint + "/" + uuid);
        storage.setAddTime(new Date());
        storage.setUpdateTime(new Date());
        storage.setDeleted(false);
        storageMapper.insert(storage);
        Storage storage1 = queryByKey(key);
        return storage1;
    }

    private Storage queryByKey(String key) {
        Storage storage = storageMapper.selectByKey(key);
        return storage;
    }

    private String generateKey(String originalFilename) {
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String key = null;
        key = CharUtil.getRandomString(20) + suffixName;
        return key;
    }
}
