package com.cskaoyan.service.user;

import com.cskaoyan.bean.vo.PageData;

public interface AddressService {
    PageData getAddress(int page, int limit, String name, String userId, String sort, String order);
}
