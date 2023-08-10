package com.fw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fw.entity.Article;
import org.apache.ibatis.annotations.Update;


public interface ArticleMapper extends BaseMapper<Article> {

    @Update("UPDATE sg_article SET del_flag=1 WHERE id=#{id} AND del_flag=0")
    int UpdateDel_flag(Long id);

        @Update("UPDATE sg_article\n SET view_count = #{viewCount} WHERE id = #{id})
    void UpdateV(@Param("id") Long id,@Param("viewCount") Long viewCount);
}
