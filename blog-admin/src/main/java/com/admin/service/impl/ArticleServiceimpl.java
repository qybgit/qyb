package com.admin.service.impl;


import com.admin.dao.mapper.ArticleMapper;
import com.admin.dao.pojo.LoginUser;
import com.admin.dao.pojo.SysUser;
import com.admin.service.ArticleService;
import com.admin.service.CommentService;
import com.framework.dao.pojo.*;


import com.framework.vo.ArticleBodyVo;
import com.framework.vo.ArticleVo;
import com.framework.vo.CommentVo;
import com.framework.vo.Result;
import com.framework.vo.params.ArticleBodyParam;
import com.framework.vo.params.ArticleParam;
import com.framework.vo.params.PageParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceimpl implements ArticleService {

    @Resource
    ArticleMapper articleMapper;
    @Resource
    CategoryServiceimpl categoryService;
    @Resource
    CommentService commentService;
    @Resource
    TagServiceimpl tagService;

    /**
     * 所有文章
     *
     * @return
     */
    @Override
    public Result findAllArticle(PageParams params) {
        if (params.getPage() == 0 || params.getPage() == null && params.getPageSize() == 0 || params.getPageSize() == null) {
            return Result.fail(400, "参数错误", null);
        }
        PageHelper.startPage(params.getPage(), params.getPageSize());
        //        /**
//         * PageHelper 能给下面运行的sql语句插入limit
//         * 实现分页置顶排序
//         */
        List<Article> articleList = articleMapper.findAllArticle();
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        List<ArticleVo> articleVoList = copyList(pageInfo.getList(), false, false, false);
        return Result.success(articleVoList);
    }

    /**
     * 标签分类文章
     *
     * @param id
     * @return
     */
    @Override
    public Result selectArticleByTagId(Integer id) {
        List<Long> articleIdList = tagService.selectAListId(id);
        List<Article> articleList = articleMapper.selectArticleByTagId(id);

        if (articleList == null || articleList.size() == 0) {
            return Result.fail(400, "没有文章", null);
        }

        List<ArticleVo> articleVoList = copyList(articleList, false, false, false);
//        for (Long l : articleIdList) {
//            articleVoList.add(selectArticleById(l));
//        }


        return Result.success(articleVoList);
    }

    /**
     * 分类id查找
     *
     * @param id
     * @return
     */
    @Override
    public Result selectArticle(int id) {
        return Result.success(copyList(categoryService.selectArticle(id), false, false, false));
    }

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @Override
    public Result findArticle(Integer id) {
        Article article = articleMapper.selectArticleById(id);
        if (article == null) {
            return Result.fail(400, "文章不存在", null);
        }
        ArticleVo articleVo = copy(article, true, false, true);

        return Result.success(articleVo);
    }

    /**
     * 添加文章
     *
     * @param
     * @return
     */
    @Override
    public Result addArticle(ArticleParam articleParam) {
        if (!publishArticle(articleParam)) {
            return Result.fail(400, "发布失败", null);
        }
        return Result.success("发布成功");

    }

    private boolean publishArticle(ArticleParam articleParam) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = loginUser.getUser();
        Article article = new Article();
        BeanUtils.copyProperties(articleParam, article);
        article.setCreateDate(System.currentTimeMillis());
        article.setUpdateDate(System.currentTimeMillis());
        article.setAuthor_id(sysUser.getId());
        article.setDel_fag(0);
        if (!publish(article, articleParam)) {
            return false;
        }
        return true;

    }

    @Override
    public Result articleCount() {
        int count = articleMapper.articleCount();
        return Result.success(count);
    }

    @Override
    public Result alls() {
        List<Article> articles = articleMapper.alls();
        List<ArticleVo> articleVoList = copyList(articles, false, false, false);
        return Result.success(articleVoList);
    }

    @Override
    public Result delete(Integer id) {
        Article article = articleMapper.selectArticleById(id);
        if (article == null) {
            return Result.fail(400, "文章已删除", null);
        }
        if (!deleteArticle(id)) {
            return Result.fail(400, "删除失败", null);
        }
        return Result.success("删除成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateArticle(ArticleVo articleVo) {
        Article article = new Article();
        BeanUtils.copyProperties(articleVo, article);
        article.setUpdateDate(System.currentTimeMillis());
        article.setCategory_id(articleVo.getCategory().getId());
        try{
            articleMapper.updateArticle(article);
            articleMapper.updateArticleBody(articleVo.getArticleBodyVo());
            List<Tag> odlTagList=tagService.selectByArticleId(articleVo.getId());
            List<Tag> newTagList=articleVo.getTags();
            for (Tag tag:odlTagList){
                if (!newTagList.contains(tag)){
                    articleMapper.deleteTagArticle(tag.getId());
                }
            }
            for (Tag tag :newTagList){
                if (!odlTagList.contains(tag)){
                    articleMapper.insertTagArticle(articleVo.getId(),tag.getId());
                }
            }
        }catch (Exception e){
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(400,"修改失败",null);
        }
        return Result.success("修改成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteArticle(Integer id) {
        try {
            articleMapper.deleteArticle(id);
            articleMapper.deleteComment(id);
            articleMapper.deleteTag(id);
        } catch (Exception e) {

            throw e;

        }
        return true;
    }

//    /**
//     * 更新文章
//     *
//     * @param body_id
//     * @return
//     */
//
//    @Transactional(rollbackFor = Exception.class)
//    public boolean updateArticle(Integer body_id, long id) {
//        try {
//            articleMapper.update(body_id, id);
//        } catch (Exception e) {
//            throw e;
//        }
//        return true;
//    }

//    /**
//     * 插入body
//     *
//     * @param body
//     * @return
//     */
//
//    @Transactional(rollbackFor = Exception.class)
//    public Integer publishBody(ArticleBodyParam body, long id) {
//        ArticleBody articleBody = new ArticleBody();
//        BeanUtils.copyProperties(body, articleBody);
//        articleBody.setArticle_id(id);
//        try {
//            articleMapper.insertBody(articleBody);
//        } catch (Exception e) {
//            throw e;
//        }
//        return articleBody.getId();
//    }

    /**
     * 插入文章
     *
     * @param article
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    public boolean publish(Article article, ArticleParam articleParam) {
        ArticleBody articleBody = new ArticleBody();
        BeanUtils.copyProperties(articleParam.getBody(), articleBody);
        try {
            articleMapper.insertArticle(article);
            articleBody.setArticle_id(article.getId());
            articleMapper.insertBody(articleBody);
            articleMapper.update(articleBody.getId(), article.getId());
            List<Tag> tagList = articleParam.getTags();

            for (Tag tag : tagList) {
                articleMapper.insertArticleWithTag(tag.getId(), article.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public ArticleVo selectArticleById(Long l) {
        Article article = articleMapper.selectById(l);

        return copy(article, false, false, false);
    }

    //复制属性
    public List<ArticleVo> copyList(List<Article> articleList, boolean isBody, boolean isComment, boolean isTag) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articleList) {
            ArticleVo articleVo = copy(article, isBody, isComment, isTag);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }

    public ArticleVo copy(Article article, boolean isBody, boolean isComment, boolean isTag) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        articleVo.setUpdateDate(new DateTime(article.getUpdateDate()).toString("yyyy-MM-dd HH:mm"));
        Category category = categoryService.findById(article.getCategory_id());
        articleVo.setCategory(category);
        if (isBody) {
            ArticleBody articleBody = articleMapper.findArticleBody(article.getBody_id());
            ArticleBodyVo articleBodyVo = new ArticleBodyVo();
            BeanUtils.copyProperties(articleBody, articleBodyVo);
            articleVo.setArticleBodyVo(articleBodyVo);
        }
        if (isComment) {
            List<CommentVo> commentVo = commentService.findCommentByArticleId(article.getId());
            commentVo.forEach(System.out::println);
            if (commentVo.size() == 0) {
                articleVo.setCommentVo(null);
            } else {
                articleVo.setCommentVo(commentVo);
            }
        }
        if (isTag) {
            List<Tag> tags = tagService.selectByArticleId(articleVo.getId());
            articleVo.setTags(tags);
        }
        return articleVo;
    }
}
