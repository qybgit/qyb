package com.admin.controller;




import com.admin.service.ArticleService;
import com.admin.service.CategoryService;
import com.framework.dao.pojo.Category;
import com.framework.dao.pojo.Tag;
import com.framework.vo.Result;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@per.hasPermission('system:category')")

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
     * 查找所有分类
     * @return
     */
    @GetMapping("alls")
    public Result selectCategorys(){
        return categoryService.findAlls();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    @PreAuthorize("@per.hasPermission('system:category')")

    public Result delCategory(@PathVariable("id") Integer id){
        return categoryService.delCategory(id);
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping("add")
    @PreAuthorize("@per.hasPermission('system:category')")

    public Result addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PostMapping("revise")
    @PreAuthorize("@per.hasPermission('system:category')")

    public Result revise(@RequestBody Category category){
        return categoryService.revise(category);
    }
}
