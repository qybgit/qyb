package com.admin.service.impl;

import com.admin.dao.mapper.ArticleMapper;
import com.admin.dao.mapper.CategoryMapper;
import com.admin.dao.mapper.SearchMapper;
import com.admin.service.SearchService;


import com.admin.vo.params.SearchArticle;
import com.framework.dao.pojo.Article;
import com.framework.dao.pojo.Category;
import com.framework.dao.pojo.Tag;
import com.framework.vo.ArticleVo;
import com.framework.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchServiceimpl implements SearchService {
    @Resource
    ArticleServiceimpl articleServiceimpl;
    @Resource
    ArticleMapper articleMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    SearchMapper mapper;
    @Override
    public Result search(String text) {
        if (StringUtils.isBlank(text)){
            return Result.success(null);
        }
        List<Article> articleList=mapper.search(text);
        List<ArticleVo> articleVoList=articleServiceimpl.copyList(articleList,false,false,false);
        if (articleVoList==null||articleVoList.size()<1)
            return Result.success(null);
        return Result.success(articleVoList);
    }

    @Override
    public Result searchArticle(SearchArticle searchArticle) {
        Tag tag=searchArticle.getTag();
        Category category=searchArticle.getCategory();
        if (tag.getId() == null&&category.getId()==null){
            return Result.fail(400,"请选择条件",null);
        }
        List<Article> articles = null;
        if (tag.getId() != null&&category.getId()==null){
            articles=articleMapper.selectArticleByTagId(tag.getId());
        }
        if (tag.getId() == null&&category.getId()!=null){
            articles=categoryMapper.selectArticle(category.getId());
        }if (tag.getId() != null&&category.getId()!=null){
            articles=mapper.selectArticleByTagIdCategoryId(tag.getId(),category.getId());
        }
        List<ArticleVo> articleVoList=articleServiceimpl.copyList(articles,false,false,false);
        return Result.success(articleVoList);
    }
}
