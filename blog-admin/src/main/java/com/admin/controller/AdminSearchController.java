package com.admin.controller;

import com.admin.service.SearchService;
import com.admin.vo.params.Search;
import com.admin.vo.params.SearchArticle;
import com.framework.dao.pojo.Category;
import com.framework.dao.pojo.Tag;
import com.framework.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin")
public class AdminSearchController {
    @Resource
    SearchService searchService;
    @PostMapping("/search/article")
    public Result searcharticle(@RequestBody SearchArticle searchArticle){
        return searchService.searchArticle(searchArticle);
    }
    @PostMapping("/search")
    public Result search(@RequestBody Search search){
        return searchService.search(search.getText());
    }
}
