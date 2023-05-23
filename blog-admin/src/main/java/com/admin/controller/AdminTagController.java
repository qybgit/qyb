package com.admin.controller;

import com.framework.dao.pojo.Tag;
import com.admin.service.ArticleService;
import com.admin.service.TagService;
import com.framework.vo.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin/tag")
public class AdminTagController {
    @Resource
    TagService tagService;
    @Resource
    ArticleService articleService;
    /**
     * all tag
     */
    @GetMapping("all")
    public Result allTag(){
        return tagService.selectAll();
    }

    /**
     * all tag
     */
    @GetMapping("alls")
    public Result allTags(){
        return tagService.selectAlls();
    }


    /**
     * 根据标签查文章
     */
    @GetMapping("{id}")
    @PreAuthorize("@per.hasPermission('system:tag')")

    public Result tagById(@PathVariable("id")Integer id) {
        return articleService.selectArticleByTagId(id);
    }

    /**
     * 添加标签
     * @param
     * @return
     */
//    @PreAuthorize("@per.hasPermission('system:tag:add')")
    @PostMapping("add")
    @PreAuthorize("@per.hasPermission('system:tag')")

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

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    @PreAuthorize("@per.hasPermission('system:tag')")

    public Result delTag(@PathVariable("id") Integer id){
        return tagService.delTag(id);
    }

    /**
     * 更新标签
     * @param tag
     * @return
     */
    @PostMapping("revise")
    @PreAuthorize("@per.hasPermission('system:tag')")

    public Result revise(@RequestBody Tag tag){
        return tagService.revise(tag);
    }
}
