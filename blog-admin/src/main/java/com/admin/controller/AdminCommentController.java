package com.admin.controller;


import com.admin.service.CommentService;


import com.admin.vo.params.Search;
import com.framework.vo.Result;
import com.framework.vo.params.CommentParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin/comment")

public class AdminCommentController {
    @Resource
    CommentService commentService;

    /**
     * 添加评论
     * @param commentParam
     * @return
     */
    @RequestMapping("add")
    @CrossOrigin(origins = "*")
    public Result add(@RequestBody CommentParam commentParam) {

        return commentService.addComment(commentParam);
    }

    /**
     * 获取所有评论
     * @return
     */
    @GetMapping("all")
    public Result selectAll(){
        return commentService.selectAll();
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @PostMapping("delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        return commentService.delete(id);
    }

    /**
     * 搜索文章
     * @param text
     * @return
     */
    @PostMapping("/search")
    public Result search(@RequestBody Search text){
        return commentService.search(text);
    }
}
