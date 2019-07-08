package com.cskaoyan.mall_admin.controller.user;

import com.cskaoyan.bean.User;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
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
        PageData pageData = userService.getUser(page, limit, username, mobile, sort, order);
        ResponseVO<PageData<List<User>>> vo = new ResponseVO<PageData<List<User>>>(0, pageData, "ok");
        return vo;
    }
}
