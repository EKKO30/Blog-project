package com.fw.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
@TableName("sg_article")
public class Article {

    @TableId
    private Long id;
    /**
    * 标题
    */
    private String title;
    /**
    * 文章内容
    */
    private String content;
    /**
    * 文章摘要
    */
    private String summary;
    /**
    * 所属分类id
    */
    private Long categoryId;
    /**
     * 所属分类id对应的名字
     */
    @TableField(exist = false)
    private String categoryName;
    /**
    * 缩略图
    */
    private String thumbnail;
    /**
    * 是否置顶（0否，1是）
    */
    private String isTop;
    /**
    * 状态（0已发布，1草稿）
    */
    private String status;
    /**
    * 访问量
    */
    private Long viewCount;
    /**
    * 是否允许评论 1是，0否
    */
    private String isComment;

    private Long createBy;

    //插入时填充字段
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //修改时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //修改时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
    * 删除标志（0代表未删除，1代表已删除）
    */
    private Integer delFlag;

    @TableField(exist = false)
    private List<Long> tags;

    public Article(Long id, long viewCount) {
        this.id=id;
        this.viewCount=viewCount;
    }
}
