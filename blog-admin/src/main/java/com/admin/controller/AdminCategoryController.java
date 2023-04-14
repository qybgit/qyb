package com.admin.controller;




import com.admin.service.ArticleService;
import com.admin.service.CategoryService;
import com.framework.dao.pojo.Category;
import com.framework.dao.pojo.Tag;
import com.framework.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin/category")
public class AdminCategoryController {
    @Resource
    ArticleService articleService;
    @Resource
    CategoryService categoryService;

    /**
     * 分类id查文章
     * @param id
     * @return
     */
    @RequestMapping("{id}")
    public Result selectArticle(@PathVariable("id") int id){
        return articleService.selectArticle(id);
    }

    /**
     * 查找所有分类
     * @return
     */
    @GetMapping("all")
    public Result selectCategory(){
        return categoryService.findAll();
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public Result delCategory(@PathVariable("id") Integer id){
        return categoryService.delCategory(id);
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping("add")
    public Result addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PostMapping("revise")
    public Result revise(@RequestBody Category category){
        return categoryService.revise(category);
    }
}
