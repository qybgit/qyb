package com.api.controller;

import com.api.service.ArticleService;
import com.api.vo.Result;
import com.api.vo.params.ArticleParam;
import com.api.vo.params.PageParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Resource
    ArticleService articleService;

    /**
     * 所有文章
     */
    @RequestMapping("all")
    public Result all(@RequestBody PageParams params) {
        return articleService.findAllArticle(params);
    }

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @RequestMapping("{id}")
    public Result article(@PathVariable("id") Long id) {
        return articleService.findArticle(id);
    }

    /**
     * 增加文章
     */
    @PostMapping("add")
    public Result addArticle(@RequestBody ArticleParam articleParam) {

        return articleService.addArticle(articleParam);

    }

    /**
     * 文章数量
     * @return
     */
    @GetMapping("count")
    public Result count(){
        return  articleService.articleCount();

    }
    @PostMapping("alls")
    public Result alls(){
        return articleService.alls();
    }
}
