package com.cskaoyan.controller.mallManage;

import com.cskaoyan.bean.Keyword;
import com.cskaoyan.bean.vo.PageData;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.service.mallManageService.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KeywordController {
    @Autowired
    KeywordService keywordService;
    //admin/keyword/list?page=1&limit=20&sort=add_time&order=desc
    //admin/keyword/list?page=1&limit=20&keyword=1&sort=add_time&order=desc
    //admin/keyword/list?page=1&limit=20&keyword=&url=3&sort=add_time&order=desc
    @RequestMapping("keyword/list")
    @ResponseBody
    public ResponseVO keywordList(int page, int limit, String sort, String order,String keyword,String url){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        PageData data = keywordService.keywordList(page,limit,sort,order,keyword,url);
        responseVO.setErrno(0);
        responseVO.setErrmsg("成功");
        responseVO.setData(data);
        return responseVO;
    }
    //admin/keyword/create
    @RequestMapping("keyword/create")
    @ResponseBody
    public ResponseVO createKeyword(@RequestBody Keyword keyword){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        Keyword result = keywordService.createKeyword(keyword);
        if(result.getId() != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(result);
            return responseVO;
        }else {
            responseVO.setErrno(1);
            responseVO.setErrmsg("false");
            return responseVO;
        }
    }

    //admin/keyword/update
    @RequestMapping("keyword/update")
    @ResponseBody
    public ResponseVO updateKeyword(@RequestBody Keyword keyword){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        Keyword result = keywordService.updateKeyword(keyword);
        if(result != null){
            responseVO.setErrno(0);
            responseVO.setErrmsg("成功");
            responseVO.setData(result);
            return responseVO;
        }else {
            responseVO.setErrno(1);
            responseVO.setErrmsg("false");
            return responseVO;
        }
    }

    //admin/keyword/delete
    @RequestMapping("keyword/delete")
    @ResponseBody
    public ResponseVO deleteKeyword(@RequestBody Keyword keyword){
        ResponseVO<Object> responseVO = new ResponseVO<>();
        int result = keywordService.deleteKeyword(keyword);
        if(result >= 1){
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
