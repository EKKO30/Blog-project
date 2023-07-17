package com.fw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.constant.SystemConstants;
import com.fw.dto.ArticleDataDto;
import com.fw.dto.ArticleListDto;
import com.fw.dto.HotArticleDto;
import com.fw.dto.PageDto;
import com.fw.entity.Article;
import com.fw.entity.ArticleTag;
import com.fw.entity.Category;
import com.fw.entity.ResponseResult;
import com.fw.mapper.ArticleMapper;
import com.fw.service.ArticleService;
import com.fw.service.ArticleTagService;
import com.fw.service.CategoryService;
import com.fw.service.UserService;
import com.fw.utils.BeanCopyUtils;
import com.fw.utils.RedisCache1;
import com.fw.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    CategoryService categoryService;

    @Resource
    RedisCache1 redisCache1;

    @Resource
    ArticleTagService articleTagService;

    @Resource
    ArticleMapper articleMapper;

    //查询热门文章 封装成ResponseResult返回
    @Override
    public List<HotArticleDto> hotArticleList() {
        //查询包装器
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //文章需要是正式文章，不能是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //文章根据浏览量降序排序
        queryWrapper.orderByDesc(Article::getViewCount);

        //分页，查1页，十个数据，
        Page<Article> articlePage=new Page<>(1,10);
        page(articlePage,queryWrapper);

        //获得数据列表
        List<Article> list=articlePage.getRecords();

//        List<HotArticleDto> list1=new ArrayList<>();
//
//        //将Articles包装成HotArticlesDto
//        for (Article e:list
//             ) {
//            HotArticleDto hotArticleDto=new HotArticleDto();
//            BeanUtils.copyProperties(e,hotArticleDto);
//            list1.add(hotArticleDto);
//        }

        //转化成vo
        List<HotArticleDto> hotArticleDtoList = BeanCopyUtils.copyBeanList(list, HotArticleDto.class);

        return hotArticleDtoList;
    }



    //在分类和首页上显示出文章，分页
    @Override
    public PageDto articleList(Integer pageNum,Integer pageSize,Long categoryId) {
        //查询包装器
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //如果是要分类,需要对比文章的分类和当前分类是否一致
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //文章需要是正式文章，不能是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //文章置顶
        queryWrapper.orderByDesc(Article::getIsTop);


        //分页
        Page<Article> articlePage=new Page<>(pageNum,pageSize);
        page(articlePage,queryWrapper);

        //查询分类名字
        List<Article> articles=articlePage.getRecords();
        //获得每一个article的id再根据id获得name
        for (Article a:articles
             ) {
            Category category = categoryService.getById(a.getCategoryId());
            a.setCategoryName(category.getName());

            //从redis中获得浏览量
            Long id=a.getId();
            Integer articleViewCount = redisCache1.getCacheMapValue("ArticleViewCount", id.toString());
            a.setViewCount(articleViewCount.longValue());
        }

        //封装
        List<ArticleListDto> list=BeanCopyUtils.copyBeanList(articlePage.getRecords(),ArticleListDto.class);
        PageDto pageDto=new PageDto(list,articlePage.getTotal());

        return pageDto;
    }

    //显示文章详情
    @Override
    public ArticleDataDto getArticleData(Long id) {
        Article a=getById(id);
        Integer articleViewCount = redisCache1.getCacheMapValue("ArticleViewCount", id.toString());
        a.setViewCount(articleViewCount.longValue());

        ArticleDataDto articleDataDto = BeanCopyUtils.copyBean(a, ArticleDataDto.class);
        Category cate = categoryService.getById(articleDataDto.getCategoryId());

        if(cate != null){
            articleDataDto.setCategoryName(cate.getName());
        }

        return articleDataDto;
    }

    @Override
    public Object updateViewCount(Long id) {
        redisCache1.incrementCacheMapValue("ArticleViewCount",id.toString(),1);
        return true;
    }


    //写博文
    @Override
    public Object addArticle(Article article) {
        //保存文章
        Long userId = SecurityUtils.getUserId();
        article.setCreateBy(userId);
        save(article);

        //获得文章对应的标签
        List<ArticleTag> collect = article.getTags().stream()
                .map(tagid -> new ArticleTag(article.getId(), tagid))
                .collect(Collectors.toList());


        //将对象存储到文章标签关联数据库中
        for (ArticleTag articleTag:collect
             ) {
            articleTagService.save(articleTag);
        }

        return true;
    }

    //通过标题和摘要进行模糊分页查询
    @Override
    public PageDto getByCondition(int pageNum,int pageSize,String title,String summary) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(title),Article::getTitle,title);
        queryWrapper.like(StringUtils.hasText(summary),Article::getSummary,summary);

        Page<Article> articlePage=new Page(pageNum,pageSize);
        page(articlePage,queryWrapper);

        PageDto pageDto=new PageDto(articlePage.getRecords(),articlePage.getTotal());
        return pageDto;
    }

    //修改文章
    @Override
    public boolean updateArticle(Article article) {
        return updateById(article);
    }

    @Override
    public int delArticle(Long id) {
        return articleMapper.UpdateDel_flag(id);
    }
}











