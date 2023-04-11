package com.api.service.impl;

import com.api.dao.mapper.CommentMapper;
import com.api.dao.pojo.Comment;
import com.api.dao.pojo.SysUser;
import com.api.service.CommentService;
import com.api.service.SysUserService;
import com.api.util.UserThreadLocal;
import com.api.vo.CommentVo;
import com.api.vo.Result;
import com.api.vo.SysUserVo;
import com.api.vo.params.CommentParam;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceimpl implements CommentService {
    @Resource
    CommentMapper commentMapper;
    @Resource
    SysUserService sysUserService;

    /**
     * 文章id找评论
     *
     * @param id
     * @return
     */
    @Override
    public List<CommentVo> findCommentByArticleId(long id) {
        List<Comment> comment = commentMapper.findCommentByArticleId(id);
        if (comment == null)
            return null;
       List<CommentVo>  commentVo = copyCommentList(comment,id);
       commentVo.forEach(System.out::println);
        return commentVo;

    }

    @Override
    public Result addComment(CommentParam commentParam) {
        if (StringUtils.isBlank(commentParam.getContent()))
            return Result.fail(400,"请重新填写",null);
        SysUser sysUser= UserThreadLocal.get();
        commentParam.setTo_uid(sysUser.getId());
        Comment comment=new Comment();


        comment.setCreateDate(System.currentTimeMillis());
        BeanUtils.copyProperties(commentParam,comment);
        if (insertComment(comment)){
return Result.success(comment.getId()   );
        }else
        return Result.fail(405,"评论失败",null);
    }


    @Transactional(rollbackFor = Exception.class)

    public boolean insertComment(Comment comment) {
        try {
            commentMapper.insertComment(comment);
            return true;
        }catch (Exception e)
        {
            throw  e;
        }

    }


    private List<CommentVo> copyCommentList(List<Comment> commentList,long id){
         List<CommentVo> commentVoList=new ArrayList<>();
         for (Comment comment:commentList){
             CommentVo commentVo=copyComment(comment,id);
             commentVoList.add(commentVo);
         }
         return commentVoList;
    }
    private CommentVo copyComment(Comment comment,long id) {
        SysUserVo sysUser = sysUserService.selectUserById(comment.getTo_uid());
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        commentVo.setToUser(sysUser);

        List<Comment> commentList = commentMapper.findCommentByparentId(commentVo.getId(),id);
        List<CommentVo> commentVoList=new ArrayList<>();
        if (commentList == null) {
            commentVo.setChildren(null);
            return commentVo;
        } else {
           for (Comment comment1:commentList){
               CommentVo commentVo1=copyComment(comment1,id);
               commentVoList.add(commentVo1);
            }
           commentVo.setChildren(commentVoList);

           return commentVo;
       }


        }
    }
