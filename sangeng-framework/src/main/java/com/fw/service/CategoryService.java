package com.fw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fw.dto.CategoryDto;
import com.fw.dto.PageDto;
import com.fw.entity.Category;
import com.fw.entity.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-06-04 14:58:41
 */
public interface CategoryService extends IService<Category> {

    //返回分类名列表
    List<CategoryDto> getCategoryList();

    List<CategoryDto> getCategoryList1();

    void export(HttpServletResponse response);

    PageDto getAllList(int pageNum, int pageSize, String name, String status);

    boolean addCategory(Category category);

    Boolean updateOne(Category category);

    Object delCategory(Long id);
}
