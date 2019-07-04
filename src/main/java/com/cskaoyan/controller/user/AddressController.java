package com.cskaoyan.controller.user;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.vo.ResponseVO2;
import com.cskaoyan.service.user.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public ResponseVO getAddress(int page, int limit, String sort, String order,
                                 @RequestParam(defaultValue = "")String name, @RequestParam(defaultValue = "") String userId) {
        ResponseVO2 vo2 = addressService.getAddress(page, limit, name, userId, sort, order);
        ResponseVO<ResponseVO2<List<Address>>> vo = new ResponseVO<ResponseVO2<List<Address>>>(0, vo2, "ok");
        return vo;
    }
}
