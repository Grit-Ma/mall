package com.cskaoyan.mall_admin.service.mallManageService;

import com.cskaoyan.bean.Issue;
import com.cskaoyan.bean.vo.PageData;

public interface IssueService {
    PageData issueList(int page, int limit, String sort, String order, String question);

    Issue createIssue(Issue issue);

    Issue updateIssue(Issue issue);

    int deleteIssue(Issue issue);
}
