package com.fw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fw.entity.Link;
import org.apache.ibatis.annotations.Update;


/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-08 16:52:36
 */
public interface LinkMapper extends BaseMapper<Link> {


    @Update("UPDATE sg_link SET del_flag=1 WHERE id=#{id} AND del_flag=0")
    int UpdateDel_flag(Long id);

}
