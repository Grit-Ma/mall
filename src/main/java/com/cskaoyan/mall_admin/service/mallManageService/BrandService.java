package com.cskaoyan.mall_admin.service.mallManageService;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.vo.PageData;

public interface BrandService {
    PageData brandList(int page, int limit, String sort, String order, Integer id, String name);

    Brand createBrand(Brand brand);

    Brand updateBrand(Brand brand);

    int deleteBrand(Brand brand);
}
