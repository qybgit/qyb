package com.admin.controller;
import com.admin.service.ArticleService;
import com.framework.vo.ArticleVo;
import com.framework.vo.Result;
import com.framework.vo.params.ArticleParam;
import com.framework.vo.params.PageParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin/article")
public class AdminArticleController {
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
    @GetMapping("{id}")
    public Result article(@PathVariable("id") Integer id) {
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

    /**
     * 获取文章不包括内容
     * @return
     */
    @GetMapping("alls")
    public Result alls(){
        return articleService.alls();
    }

    /**
     * 获取文章数据只包括标签
     * @return
     */
    @GetMapping("homeArticle")
    public Result allHome(){
        return articleService.allHome();
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        return articleService.delete(id);
    }

    /**
     * 修改文章
     * @param articleVo
     * @return
     */
    @PostMapping("updateArticle")
    public Result updateArticle(@RequestBody ArticleVo articleVo){
        return articleService.updateArticle(articleVo);
    }

    /**
     * 用户文章
     * @return
     */
    @GetMapping("myArticle")
    public Result articleByUid(){
        return articleService.articleByUid();
    }
}
