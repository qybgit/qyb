package com.api.service.impl;
import com.api.dao.mapper.CategoryMapper;
import com.api.dao.pojo.Article;
import com.api.dao.pojo.Category;
import com.api.service.CategoryService;
import com.api.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceimpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    /**
     * 通过id查分类
     * @param category_id
     * @return
     */
    @Override
    public Category findById(int category_id) {
        Category category=categoryMapper.findById(category_id);
        return category;
    }

    /**
     * all分类
     * @return
     */
    @Override
    public Result findAll() {
        List<Category> categories=categoryMapper.selectAll();
        return Result.success(categories) ;
    }


    public List<Article> selectArticle(int id) {
        List<Article> article=categoryMapper.selectArticle(id);
        return article;
    }
}
