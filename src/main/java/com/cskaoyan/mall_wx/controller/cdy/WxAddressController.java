package com.cskaoyan.mall_wx.controller.cdy;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_wx.service.cdy.WxAddressService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("wx/address")
public class WxAddressController {

    @Autowired
    WxAddressService wxAddressService;

    @GetMapping("list")
    public ResponseVO list(HttpServletRequest request) {
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        ResponseVO vo = new ResponseVO();
        if (userId == null) {
            return vo.unlogin(vo);
        }
        List<Address> data = wxAddressService.queryByUid(userId);
        vo.setData(data);
        return vo.ok(vo);
    }
    //address/detail
    //address/save
    //address/delete
}
