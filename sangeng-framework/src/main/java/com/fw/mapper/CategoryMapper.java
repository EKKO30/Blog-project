package com.fw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fw.entity.Category;
import org.apache.ibatis.annotations.Update;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-04 14:58:41
 */
public interface CategoryMapper extends BaseMapper<Category> {


    @Update("UPDATE sg_category SET del_flag=1 WHERE id=#{id} AND del_flag=0")
    int UpdateDel_flag(Long id);

}
