package com.fw.Job;

import com.fw.entity.Article;
import com.fw.mapper.ArticleMapper;
import com.fw.utils.RedisCache1;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

//定时任务
@Component
public class UpdateViewCountJob {

    @Resource
    RedisCache1 redisCache1;

    @Resource
    ArticleMapper articleMapper;

    //每5秒从redis中更新访问量到mysql中
    @Scheduled(cron = "0/5 * * * * ?")
    public void UpdateViewCount(){
        Map<String, Integer> articleViewCount = redisCache1.getCacheMap("ArticleViewCount");

        for (Map.Entry<String, Integer> entry : articleViewCount.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            articleMapper.UpdateV(Long.valueOf(key),Long.valueOf(value.longValue()));
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }
}














