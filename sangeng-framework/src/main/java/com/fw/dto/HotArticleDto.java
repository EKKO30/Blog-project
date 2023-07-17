package com.fw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//用于返回热门文章的id 标题 访问量
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleDto {

    private Long id;

    //标题
    private String title;

    //访问量
    private Long viewCount;
}
