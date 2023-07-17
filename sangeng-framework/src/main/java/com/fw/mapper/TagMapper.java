package com.fw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fw.entity.Tag;
import org.apache.ibatis.annotations.Update;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-13 15:46:30
 */
public interface TagMapper extends BaseMapper<Tag> {

    @Update("UPDATE sg_tag SET del_flag=1 WHERE id=#{id} AND del_flag=0")
    int UpdateDel_flag(Long id);

}
