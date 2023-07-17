package com.fw.Controller;

import com.fw.dto.PageDto;
import com.fw.entity.Comment;
import com.fw.entity.ResponseResult;
import com.fw.enums.ServiceImplEnum;
import com.fw.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论", description = "评论相关接口")
public class CommentController {

    @Resource
    CommentService commentService;

    //显示文章评论
    @GetMapping("/commentList")
    public ResponseResult CommentList(Long articleId,Integer pageNum,Integer pageSize){
        PageDto pageDto=commentService.commentList(ServiceImplEnum.ARTICLE_COMMENT, articleId,pageNum,pageSize);
        return ResponseResult.okResult(pageDto);
    }

    //添加评论
    @PostMapping
    public ResponseResult addcomment(@RequestBody Comment comment){
        boolean f=commentService.addcomment(comment);
        return ResponseResult.okResult("评论发送成功");
    }

    //显示友链评论
    @ApiOperation(value = "友链评论",notes = "获得所有友链的评论接口")
    @GetMapping("/linkCommentList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示的数量")
    })
    public ResponseResult LinkCommentList(Integer pageNum,Integer pageSize){
        PageDto pageDto=commentService.commentList(ServiceImplEnum.LINK_COMMENT, null,pageNum,pageSize);
        return ResponseResult.okResult(pageDto);
    }
}
