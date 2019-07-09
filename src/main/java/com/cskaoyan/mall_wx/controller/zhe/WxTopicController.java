package com.cskaoyan.mall_wx.controller.zhe;

import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.bean.wx.pagedata.TopicPageData;
import com.cskaoyan.mall_wx.service.zhe.WxTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("wx")
public class WxTopicController {
    @Autowired
    WxTopicService topicService;
    //wx/topic/list?page=1&size=10
    @RequestMapping("topic/list")
    @ResponseBody
    public ResponseVO topicList(int page, int size){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        TopicPageData data = topicService.topicList(page,size);
        if(data != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(data);
            return responseVO;
        }else {
            responseVO.setErrmsg("failure");
            return responseVO;
        }
    }

    //wx/topic/detail?id=266
    @RequestMapping("topic/detail")
    @ResponseBody
    public ResponseVO topicDetail(Integer id){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        Topic topic = topicService.topicDetail(id);
        if(topic != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            HashMap<String, Object> map = new HashMap<>();
            map.put("goods",topic.getGoods());
            map.put("topic",topic);
            responseVO.setData(map);
            return responseVO;
        }
        responseVO.setErrmsg("failure");
        return responseVO;
    }

    //wx/topic/related?id=295       //返回数据库前四条数据
    @RequestMapping("topic/related")
    @ResponseBody
    public ResponseVO topicRelated(Integer id){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        List<Topic> list = topicService.topicRelated(id);
        if(list != null ){
            responseVO.setData(list);
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
        }
        responseVO.setErrmsg("failure");
        return responseVO;
    }

}
