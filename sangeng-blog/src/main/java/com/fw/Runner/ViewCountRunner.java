package com.fw.Runner;

import com.fw.entity.Article;
import com.fw.mapper.ArticleMapper;
import com.fw.service.ArticleService;
import com.fw.utils.RedisCache1;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    ArticleService articleService;

    @Resource
    ArticleMapper articleMapper;

    @Resource
    RedisCache1 redisCache1;

    //应用启动时运行
    @Override
    public void run(String... args) throws Exception {

        //启动时从数据库中将浏览量读到redis中
        Map<String,Integer> map=new HashMap<>();

        List<Article> list = articleService.list();

        for (Article article:list
             ) {
            map.put(Long.toString(article.getId()), article.getViewCount().intValue());
        }

        //查询博客信息  id  viewCount
//        List<Article> articles = articleMapper.selectList(null);
//        Map<String, Integer> viewCountMap = articles.stream()
//                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
//                    return article.getViewCount().intValue();//
//                }));

        redisCache1.setCacheMap("ArticleViewCount",map);
        Map<String, Integer> articleViewCount = redisCache1.getCacheMap("ArticleViewCount");
        System.out.println("----------------------------------");
        System.out.println(articleViewCount.get("1"));
        System.out.println("----------------------------------");
    }
}
