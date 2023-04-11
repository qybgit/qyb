package com.api.controller;

import com.api.service.SearchService;
import com.api.vo.Result;
import com.api.vo.params.Search;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController

public class SearchController {
    @Resource
    SearchService searchService;

    @PostMapping("/search")
    public Result search(@RequestBody Search search){
        return searchService.search(search.getText());
    }
}
