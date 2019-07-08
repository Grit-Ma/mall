package com.cskaoyan.mall_admin.controller.promotion;

import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_admin.service.promotion.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * created by ZengWei
 * on 2019/7/5
 */

@Controller
public class TopicController {

    @Autowired
    TopicService topicService;

    @RequestMapping("topic/list")
    @ResponseBody
    public ResponseVO list(int page, int limit, String sort, String order, String title, String subtitle) {
        ResponseVO vo = new ResponseVO();
        PageData data = topicService.getTopicListData(page, limit, sort, order, title, subtitle);
        vo.setErrmsg("成功");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("topic/create")
    @ResponseBody
    public ResponseVO insert(@RequestBody Topic topic) {
        ResponseVO vo = new ResponseVO();
        System.out.println(topic);
        vo = topicService.insertTopic(topic);
        return vo;
    }


    @RequestMapping("topic/update")
    @ResponseBody
    public ResponseVO update(@RequestBody Topic topic) {
        ResponseVO vo = new ResponseVO();
        vo = topicService.updateTopic(topic);
        return vo;
    }

    @RequestMapping("topic/delete")
    @ResponseBody
    public ResponseVO delete(@RequestBody Topic topic) {
        ResponseVO vo = new ResponseVO();
        vo = topicService.deleteTopic(topic);
        return vo;
    }


}
