package com.cskaoyan.service.sys.impl;

import com.cskaoyan.bean.sys.Storage;
import com.cskaoyan.mapper.sys.StorageMapper;
import com.cskaoyan.service.sys.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    StorageMapper storageMapper;

    @Override
    public int insertStorage(Storage storage){
        return storageMapper.insert(storage);
    }

    @Override
    public List<Storage> fuzzyQuery(String key, String name, String sort, String order) {
        List<Storage> storages = storageMapper.fuzzyQuery(key, name, sort, order);
        return storages;
    }

    @Override
    public int updateStorage(Storage storage) {
        int i = storageMapper.updateByPrimaryKeySelective(storage);
        return i;
    }
}
