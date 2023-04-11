package com.admin.controller;

import com.framework.dao.pojo.Tag;
import com.framework.service.ArticleService;
import com.framework.service.TagService;
import com.framework.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("tag")
public class AdminTagController {
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
