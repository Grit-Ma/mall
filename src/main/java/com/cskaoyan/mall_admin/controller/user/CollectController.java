package com.cskaoyan.mall_admin.controller.user;

import com.cskaoyan.bean.Collect;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.user.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/collect")
public class CollectController {
    @Autowired
    CollectService collectService;

    /**
     * admin/collect/list
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseVO collectQuery(int page, int limit, String sort, String order,
                                   @RequestParam(defaultValue = "")String userId, @RequestParam(defaultValue = "") String valueId) {
        PageData pageData = collectService.getCollect(page, limit, userId, valueId, sort, order);
        ResponseVO<PageData<List<Collect>>> vo = new ResponseVO<PageData<List<Collect>>>(0, pageData, "ok");
        return vo;
    }
}
