package com.admin.service.impl;
import com.admin.dao.mapper.CategoryMapper;
import com.admin.dao.pojo.LoginUser;
import com.admin.service.CategoryService;
import com.admin.service.SysUserService;
import com.admin.vo.CategoryVo;
import com.framework.dao.pojo.Article;
import com.framework.dao.pojo.Category;

import com.framework.dao.pojo.Tag;
import com.framework.vo.Result;
import com.framework.vo.SysUserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceimpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    SysUserService sysUserService;
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
        List<CategoryVo> categoryVoList=copyList(categories);
        return Result.success(categoryVoList) ;
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @Override
    public Result delCategory(Integer id) {
        Category category=categoryMapper.findById(id);
        if (category.getDel_fag()==1){
            return Result.fail(400,"此分类已删除",null);
        }
        if (!deleteCategory(id))
            return Result.fail(400,"删除失败",null);
        return Result.success("删除成功");

    }

    /**
     * 添加标签
     * @param category
     * @return
     */
    @Override
    public Result addCategory(Category category) {
        if(StringUtils.isBlank(category.getCategory_name())){
            return Result.fail(400,"请填写分类",null);
        }
        Category category1=categoryMapper.findByCategoryName(category.getCategory_name());
        if (category1!=null){
            return Result.fail(400,"分类已存在",null);
        }
        if(!addCate(category)){
            return  Result.fail(400,"添加失败",null);
        }
        return Result.success("添加成功");
    }

    @Override
    public Result revise(Category category) {
        if (StringUtils.isBlank(category.getCategory_name())){
            return Result.fail(400,"请重现填写分类",null);
        }
        Category category1=categoryMapper.selectByCategoryName(category.getCategory_name());
        if (category1!=null){
            return Result.fail(400,"此分类已存在",null);
        }
        if (!updateTag(category)){
            return  Result.fail(400,"更新失败",null);
        }
        return  Result.success("更新成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateTag(Category category) {
        try{
            categoryMapper.updateTagByName(category);
        }catch (Exception e){
            throw e;
        }
        return true;
    }

    private boolean addCate(Category category) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        category.setCreateBy_id(loginUser.getUser().getId());
        category.setDel_fag(0);
        try {
            categoryMapper.insertCategory(category);
        }catch (Exception e){
            throw e;
        }
        return true;
    }

    @Transactional( rollbackFor = Exception.class)
    public boolean deleteCategory(Integer id) {
        try{
            categoryMapper.updateCategory(id);
        }catch (Exception e){
            throw e;
        }
        return true;
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> categoryVoList=new ArrayList<>();
        for (Category category:categories){
            CategoryVo categoryVo=copy(category);
            categoryVoList.add(categoryVo);
        }
        return categoryVoList;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        SysUserVo sysUserVo=sysUserService.selectUserById(category.getCreateBy_id());
        categoryVo.setSysUserVo(sysUserVo);
        return categoryVo;

    }


    public List<Article> selectArticle(int id) {
        List<Article> article=categoryMapper.selectArticle(id);
        return article;
    }
}
