package com.cskaoyan.promotion;

import com.cskaoyan.bean.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/3
 */

@Controller
public class PromotionController {

    @Autowired
    PromotionService promotionService;

    @ResponseBody
    @RequestMapping("admin/ad/list")
    public List<Ad> findAllAds(){
        List<Ad> ads = promotionService.findAllAds();
        return ads;
    }
}
