package com.fw.Controller;

import com.fw.dto.HotArticleDto;
import com.fw.dto.PageDto;
import com.fw.entity.Article;
import com.fw.entity.ResponseResult;
import com.fw.service.ArticleService;
import com.fw.service.CategoryService;
import com.fw.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    CategoryService categoryService;

    @Resource
    TagService tagService;

    @GetMapping("/list")
    public List<Article> test(){
        return articleService.list();
    }

    @GetMapping("/hotArticleList")
    public ResponseResult<List<HotArticleDto>> gethotarticle(){
        //查询热门文章 封装成ResponseResult返回
        List<HotArticleDto> list=articleService.hotArticleList();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        PageDto pageDto =articleService.articleList(pageNum,pageSize,categoryId);
        return ResponseResult.okResult(pageDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleData(@PathVariable Long id){
        Object o=articleService.getArticleData(id);
        return ResponseResult.okResult(o);
    }


    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable Long id){
        Object o=articleService.updateViewCount(id);
        return ResponseResult.okResult(o);
    }

    //用于写博文的查询所有分类
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return ResponseResult.okResult(categoryService.getCategoryList());
    }

    //用于写博文的查询所有标签
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return ResponseResult.okResult(tagService.getAll());
    }

    //写博文
    @PostMapping("/article")
    public ResponseResult article(@RequestBody Article article){
        return ResponseResult.okResult(articleService.addArticle(article));
    }
}


