package com.cskaoyan.service.user;

import com.cskaoyan.bean.vo.ResponseVO2;

public interface AddressService {
    ResponseVO2 getAddress(int page, int limit, String name, String userId, String sort, String order);
}
