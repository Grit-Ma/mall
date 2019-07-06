package com.cskaoyan.controller.mallManage;

import com.cskaoyan.bean.Issue;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.mallManageService.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IssueController {
    @Autowired
    IssueService issueService;
    //admin/issue/list?page=1&limit=20&sort=add_time&order=desc
    //admin/issue/list?page=1&limit=20&question=%E4%BD%95&sort=add_time&order=desc
    @RequestMapping("issue/list")
    @ResponseBody
    public ResponseVO issueList(int page, int limit, String sort, String order,String question){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        PageData data = issueService.issueList(page,limit,sort,order,question);
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }
    //admin/issue/create
    @RequestMapping("issue/create")
    @ResponseBody
    public ResponseVO createIssue(@RequestBody Issue issue){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        Issue issueResult = issueService.createIssue(issue);
        if(issueResult.getId() != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(issueResult);
            return responseVO;
        }else {
            responseVO.setErrno(1);
            responseVO.setErrmsg("false");
            return responseVO;
        }
    }

    //admin/issue/update
    @RequestMapping("issue/update")
    @ResponseBody
    public ResponseVO updateIssue(@RequestBody Issue issue){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        Issue issueResult = issueService.updateIssue(issue);
        if(issueResult != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(issueResult);
            return responseVO;
        }else {
            responseVO.setErrno(1);
            responseVO.setErrmsg("false");
            return responseVO;
        }
    }

    // admin/issue/delete
    @RequestMapping("issue/delete")
    @ResponseBody
    public ResponseVO deleteIssue(@RequestBody Issue issue){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        int issueResult = issueService.deleteIssue(issue);
        if(issueResult >= 1){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            return responseVO;
        }else {
            responseVO.setErrno(1);
            responseVO.setErrmsg("false");
            return responseVO;
        }
    }

}
