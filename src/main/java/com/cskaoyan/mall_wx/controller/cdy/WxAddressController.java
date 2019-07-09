package com.cskaoyan.mall_wx.controller.cdy;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.WxAddress;
import com.cskaoyan.mall_wx.service.cdy.WxAddressService;
import com.cskaoyan.mall_wx.service.cdy.WxRegionService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("wx")
public class WxAddressController {

    @Autowired
    WxAddressService wxAddressService;

    @Autowired
    WxRegionService wxRegionService;

    @GetMapping("address/list")
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

    @GetMapping("address/detail")
    public ResponseVO detail(HttpServletRequest request, @NotNull Integer id) {
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        ResponseVO vo = new ResponseVO();
        if (userId == null) {
            return vo.unlogin(vo);
        }
        WxAddress data = wxAddressService.query(userId, id);
        if (data == null) {
            return vo.errParmValue(vo);
        }
        vo.setData(data);
        return vo.ok(vo);
    }
    //address/save
    //address/delete

    @GetMapping("region/list")
    public ResponseVO regionList(Integer pid) {
        ResponseVO vo = new ResponseVO();
        List<Region> data = wxRegionService.queryByPid(pid);

        vo.setData(data);
        return vo.ok(vo);
    }

    @PostMapping("address/save")
    public ResponseVO save(HttpServletRequest request, @RequestBody Address address){
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        ResponseVO vo = new ResponseVO();
        if (userId == null) {
            return vo.unlogin(vo);
        }
        address.setUserId(userId);
        address.setDeleted(false);
        Integer data = wxAddressService.update(address);
        if (data == null) {
            return vo.errParmValue(vo);
        }
        vo.setData(data);
        return vo.ok(vo);
    }

    @PostMapping("address/delete")
    public ResponseVO delete(HttpServletRequest request, @RequestBody Address address){
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        ResponseVO vo = new ResponseVO();
        if (userId == null) {
            return vo.unlogin(vo);
        }

        Integer data = wxAddressService.delete(address.getId());
        if (data == null) {
            return vo.errParmValue(vo);
        }
        vo.setData(data);
        return vo.ok(vo);
    }
}
