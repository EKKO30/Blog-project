package com.fw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fw.dto.PageDto;
import com.fw.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-07-05 15:54:21
 */
public interface CommentService extends IService<Comment> {

    PageDto commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    boolean addcomment(Comment comment);
}
