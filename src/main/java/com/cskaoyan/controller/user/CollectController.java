package com.cskaoyan.controller.user;

import com.cskaoyan.bean.Collect;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.vo.ResponseVO2;
import com.cskaoyan.service.user.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        ResponseVO2 vo2 = collectService.getCollect(page, limit, userId, valueId, sort, order);
        ResponseVO<ResponseVO2<List<Collect>>> vo = new ResponseVO<>(0, vo2, "ok");
        return vo;
    }
}
