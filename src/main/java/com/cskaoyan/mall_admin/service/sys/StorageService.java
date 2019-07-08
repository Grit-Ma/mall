package com.cskaoyan.mall_admin.service.sys;

import com.cskaoyan.bean.sys.Storage;

import java.util.List;

public interface StorageService {

    int insertStorage(Storage storage);

    List<Storage> fuzzyQuery(String key, String name, String sort, String order);

    int updateStorage(Storage storage);
}
