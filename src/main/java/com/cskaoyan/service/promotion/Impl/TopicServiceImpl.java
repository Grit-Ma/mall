package com.cskaoyan.service.promotion.Impl;

import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.TopicExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mapper.TopicMapper;
import com.cskaoyan.service.promotion.TopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by ZengWei
 * on 2019/7/5
 */

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicMapper topicMapper;

    @Override
    public PageData getTopicListData(int page, int limit, String sort, String order, String title, String subtitle) {
        PageHelper.startPage(page,limit);
        TopicExample example = new TopicExample();
        TopicExample.Criteria criteria = example.createCriteria();
        if(title != null){
            criteria.andTitleLike("%"+title+"%");
        }
        if(subtitle != null){
            criteria.andSubtitleLike("%"+subtitle+"%");
        }
        example.setOrderByClause(sort+" "+order);
        List<Topic> topics = topicMapper.selectByExample(example);
        PageInfo<Topic> pageinfo = new PageInfo(topics);
        return new PageData(pageinfo.getList(),pageinfo.getTotal());
    }

    @Override
    public ResponseVO insertTopic(Topic topic) {
        ResponseVO vo = new ResponseVO();
        try{
            topicMapper.insert(topic);
            vo.setErrmsg("添加成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("添加失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public ResponseVO updateTopic(Topic topic) {
        ResponseVO vo = new ResponseVO();
        try{
            topicMapper.updateByPrimaryKey(topic);
            vo.setErrmsg("修改成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("修改失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }

    @Override
    public ResponseVO deleteTopic(Topic topic) {
        ResponseVO vo = new ResponseVO();
        try{
            topicMapper.deleteByPrimaryKey(topic.getId());
            vo.setErrmsg("删除成功");
            vo.setErrno(0);
        }catch (Exception e){
            vo.setErrmsg("删除失败:"+e.getMessage());
            vo.setErrno(1);
        }
        return vo;
    }
}
