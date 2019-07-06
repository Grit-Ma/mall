package com.cskaoyan.service.mallManageService.impl;

import com.cskaoyan.bean.Issue;
import com.cskaoyan.bean.IssueExample;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.mapper.IssueMapper;
import com.cskaoyan.service.mallManageService.IssueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    IssueMapper issueMapper;
    @Override
    public PageData issueList(int page, int limit, String sort, String order,String question) {
        PageHelper.startPage(page,limit);
        IssueExample example = new IssueExample();
        if(question != null && question.trim().length() != 0){
            question = "%" + question.trim() + "%";
            example.createCriteria().andQuestionLike(question);
        }
        example.createCriteria().andDeletedEqualTo(false);
        example.setOrderByClause(sort + "  " + order);
        List<Issue> list = issueMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return new PageData(pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public Issue createIssue(Issue issue) {
        Date date = new Date();
        issue.setAddTime(date);
        issue.setUpdateTime(date);
        int insert = issueMapper.insert(issue);
        return issue;
    }

    @Override
    public Issue updateIssue(Issue issue) {
        Date date = new Date();
        issue.setUpdateTime(date);
        int i = issueMapper.updateByPrimaryKeySelective(issue);
        if(i >= 1){
            return issue;
        }
        return null;
    }

    @Override
    public int deleteIssue(Issue issue) {
        issue.setDeleted(true);
        Date date = new Date();
        issue.setUpdateTime(date);
        int i = issueMapper.updateByPrimaryKeySelective(issue);
        return i;
    }

}
