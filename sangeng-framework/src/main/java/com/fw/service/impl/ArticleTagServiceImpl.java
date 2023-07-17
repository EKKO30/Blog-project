package com.fw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.entity.ArticleTag;
import com.fw.mapper.ArticleTagMapper;
import com.fw.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-07-13 18:17:31
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
