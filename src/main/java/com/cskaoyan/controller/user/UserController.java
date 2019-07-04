package com.cskaoyan.controller.user;

import com.cskaoyan.bean.User;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.vo.ResponseVO2;
import com.cskaoyan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * admin/user/list
     * 排序 desc
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseVO getUser(int page, int limit, String sort, String order,
                                @RequestParam(defaultValue = "")String username, @RequestParam(defaultValue = "") String mobile) {
        ResponseVO2 vo2 = userService.getUser(page, limit, username, mobile, sort, order);
        ResponseVO<ResponseVO2<List<User>>> vo = new ResponseVO<>(0, vo2, "ok");
        return vo;
    }
}
