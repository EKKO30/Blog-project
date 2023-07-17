package com.fw.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//文章详情
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDataDto {

    @TableId
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 所属分类id
     */
    private String categoryId;
    /**
     * 所属分类id对应的名字
     */
    private String categoryName;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 访问量
     */
    private Long viewCount;
    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;

    private Date createTime;


}
