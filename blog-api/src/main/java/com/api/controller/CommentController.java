package com.api.controller;

import com.api.service.CommentService;
import com.api.vo.Result;
import com.api.vo.params.CommentParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("comment")
@CrossOrigin(origins = "*")
public class CommentController {
    @Resource
    CommentService commentService;

    @RequestMapping("add")
    @CrossOrigin(origins = "*")
    public Result add(@RequestBody CommentParam commentParam) {

        return commentService.addComment(commentParam);
    }
}
