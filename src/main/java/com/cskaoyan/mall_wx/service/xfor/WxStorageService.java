package com.cskaoyan.mall_wx.service.xfor;

import com.cskaoyan.bean.sys.Storage;
import org.springframework.web.multipart.MultipartFile;

public interface WxStorageService {
    Storage store(MultipartFile file);
}
