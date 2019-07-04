package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.Storage;
import com.cskaoyan.mapper.sys.StorageMapper;
import com.cskaoyan.service.sys.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    StorageMapper storageMapper;

    @Override
    public int insertStorage(Storage storage){
        return storageMapper.insert(storage);
    }
}
