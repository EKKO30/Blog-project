package com.fw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fw.dto.ArticleDataDto;
import com.fw.dto.HotArticleDto;
import com.fw.dto.PageDto;
import com.fw.entity.Article;
import com.fw.entity.ResponseResult;

import java.util.List;


public interface ArticleService extends IService<Article> {

    List<HotArticleDto> hotArticleList();

    PageDto articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ArticleDataDto getArticleData(Long id);

    Object updateViewCount(Long id);

    Object addArticle(Article article);

    PageDto getByCondition(int pageNum,int pageSize,String title,String summary);

    boolean updateArticle(Article article);

    int delArticle(Long id);
}
