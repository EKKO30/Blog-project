package com.fw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.dto.CommentDto;
import com.fw.dto.PageDto;
import com.fw.entity.Comment;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.enums.ServiceImplEnum;
import com.fw.exception.SystemException;
import com.fw.mapper.CommentMapper;
import com.fw.service.CommentService;
import com.fw.service.UserService;
import com.fw.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.fw.enums.ServiceImplEnum.COMMENT_ROOT_ID;


/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-07-05 15:54:22
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    UserService userService;

    //显示评论
    @Override
    public PageDto commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {

        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        //文章评论
        if(ServiceImplEnum.ARTICLE_COMMENT.equals(commentType)){
            queryWrapper.eq(Comment::getArticleId,articleId);
        }else {
            //友链评论
            queryWrapper.eq(Comment::getType,ServiceImplEnum.LINK_COMMENT);
        }

//        //需要是当下文章的评论
//        queryWrapper.eq(ServiceImplEnum.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //需要是根评论
        queryWrapper.eq(Comment::getRootId,COMMENT_ROOT_ID);

        //分页查询
        Page<Comment> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        //拷贝列表
        List<CommentDto> list= getCommentDtolist(page.getRecords());

        //查找子评论
        for (CommentDto commentDto:list
                ) {
            //查找对应的子评论
            List<CommentDto> commentDto1=getchildren(commentDto.getId());
            commentDto.setChildren(commentDto1);
        }

        return new PageDto(list,page.getTotal());
    }


    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentDto> getchildren(Long id){

        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        //找到根评论下的所有评论
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        return getCommentDtolist(list);
    }


    //添加评论
    @Override
    public boolean addcomment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return true;
    }

    //转换CommentDtolist
    private List<CommentDto> getCommentDtolist(List<Comment> list){
        List<CommentDto> a= BeanCopyUtils.copyBeanList(list,CommentDto.class);

        for (CommentDto commentDto:a
             ) {
            //通过id获得昵称
            String nickName = userService.getById(commentDto.getCreateBy()).getNickName();
            commentDto.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(commentDto.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentDto.getToCommentUserId()).getNickName();
                commentDto.setToCommentUserName(toCommentUserName);
            }
        }

        return a;
    }
}





















