package com.cskaoyan.mall_wx.controller.cdy;

import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_wx.service.cdy.WxOrderService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxUserController {

    @Autowired
    WxOrderService wxOrderService;

    @GetMapping("user/index")
    public ResponseVO list(HttpServletRequest request) {
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        ResponseVO vo = new ResponseVO();
        if (userId == null) {
            return vo.unlogin(vo);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("order", wxOrderService.orderInfo(userId));
        vo.setData(data);
        return vo.ok(vo);
    }

}
