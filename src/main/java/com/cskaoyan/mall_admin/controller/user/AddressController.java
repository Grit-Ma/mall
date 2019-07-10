package com.cskaoyan.mall_admin.controller.user;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.user.AddressService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    /**
     * admin/address/list
     */
    @GetMapping("/list")
    @ResponseBody
    @RequiresPermissions(value = "admin:address:list")
    public ResponseVO getAddress(int page, int limit, String sort, String order,
                                 @RequestParam(defaultValue = "")String name, @RequestParam(defaultValue = "") String userId) {
        PageData pageData = addressService.getAddress(page, limit, name, userId, sort, order);
        ResponseVO<PageData<List<Address>>> vo = new ResponseVO<PageData<List<Address>>>(0, pageData, "ok");
        return vo;
    }
}
