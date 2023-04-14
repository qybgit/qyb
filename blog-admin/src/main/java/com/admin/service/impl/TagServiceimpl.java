package com.admin.service.impl;


import com.admin.dao.mapper.TagMapper;
import com.admin.dao.pojo.LoginUser;
import com.admin.service.SysUserService;
import com.admin.service.TagService;
import com.admin.vo.TagVo;
import com.framework.dao.pojo.Tag;

import com.framework.vo.Result;
import com.framework.vo.SysUserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class TagServiceimpl implements TagService {
    @Resource
    TagMapper tagMapper;
    @Resource
    SysUserService sysUserService;
    @Override
    public Result selectAll() {
        List<Tag> tagList=tagMapper.selectAll();
        List<TagVo> tagVoList=copyList(tagList);
        return Result.success(tagVoList);
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList=new ArrayList<>();
        for(Tag tag:tagList){
            TagVo tagVo=copy(tag);
            tagVoList.add(tagVo);
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo=new TagVo();
        int count=findCount(tag);
        tag.setCount(count);
        BeanUtils.copyProperties(tag,tagVo);
        SysUserVo sysUserVo=sysUserService.selectUserById(tag.getCreateBy_id());
        tagVo.setSysUserVo(sysUserVo);
        return tagVo;
    }

    //评论数量
    private int findCount(Tag tag) {

        int count=tagMapper.findCount(tag.getId());
        return  count;
    }

    /**
     * 添加标签
     * @param nickname
     * @return
     */
    @Override
    public Result addTag(String nickname) {
        if(StringUtils.isBlank(nickname)){
            return Result.fail(400,"请填写标签",null);
        }
        Tag tag=tagMapper.selectByTagName(nickname);
        if (tag!=null){
            return Result.fail(400,"标签已存在",null);
        }
        if (!addTAG(nickname))
            return Result.fail(400,"添加失败",null);
        return Result.success("添加成功");
    }

    /**
     * 查找文章标签
     * @param id
     * @return
     */
    @Override
    public List<Tag> selectByArticleId(Long id) {
        List<Tag> tags=tagMapper.selectArticleById(id);
        return tags;
    }

    @Override
    public Result selectCount() {
        int count=tagMapper.findTagCount();
        return Result.success(count);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @Override
    public Result delTag(Integer id) {
        Tag tag=tagMapper.selectById(id);
        if (tag.getDel_fag()==1){
            return Result.fail(400,"该标签已删除",null);
        }
        if (!deleteTag(id))
            return Result.fail(400,"删除失败",null);
        return Result.success("删除成功");

    }

    @Override
    public Result revise(Tag tag) {
        if (StringUtils.isBlank(tag.getTag_Name())){
            return Result.fail(400,"请重现填写标签",null);
        }
        Tag tag1=tagMapper.selectByTagName(tag.getTag_Name());
        if (tag1!=null){
            return Result.fail(400,"此标签已存在",null);
        }
        if (!updateTag(tag)){
            return  Result.fail(400,"更新失败",null);
        }
        return  Result.success("更新成功");

    }
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTag(Tag tag) {
        try{
            tagMapper.updateTagByName(tag);
        }catch (Exception e){
            throw e;
        }
        return true;
    }

    @Transactional( rollbackFor = Exception.class)
    public boolean deleteTag(Integer id) {
        try{
            tagMapper.updateTag(id);
        }catch (Exception e){
            throw e;
        }
        return true;
    }


    @Transactional( rollbackFor = Exception.class)
    public boolean addTAG(String nickname) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tag tag=new Tag();
        tag.setTag_Name(nickname);
        tag.setCreateBy_id(loginUser.getUser().getId());
        tag.setDel_fag(0);

        try {
            tagMapper.insertTag(tag);
        }catch (Exception e){
            throw  e;
        }
        return true;
    }

    public List<Long> selectAListId(Integer id){
        List<Long> aListId=tagMapper.selectAListId(id);
        return aListId;
    }


}
