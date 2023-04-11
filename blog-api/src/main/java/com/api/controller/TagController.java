package com.api.controller;

import com.api.dao.pojo.Tag;
import com.api.service.ArticleService;
import com.api.service.TagService;
import com.api.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("tag")
public class TagController {
    @Resource
    TagService tagService;
    @Resource
    ArticleService articleService;
    /**
     * all tag
     */
    @RequestMapping("all")
    public Result allTag(){
        return tagService.selectAll();
    }
    /**
     * 根据标签查文章
     */
    @RequestMapping("{id}")
    public Result tagById(@PathVariable("id")Long id) {
        return articleService.selectArticleByTagId(id);
    }

    /**
     * 添加标签
     * @param
     * @return
     */
    @PostMapping("add")
    public Result addTag(@RequestBody Tag tag){
        return tagService.addTag(tag.getTag_Name());

    }
    /**
     * 评论总数
     */
    @GetMapping("/count")
    public Result tagCount(){
        return tagService.selectCount();
    }
}
