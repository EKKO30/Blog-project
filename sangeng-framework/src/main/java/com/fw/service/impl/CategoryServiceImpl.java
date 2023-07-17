package com.fw.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.ExcelVo.CategoryExcelVo;
import com.fw.constant.SystemConstants;
import com.fw.dto.CategoryDto;
import com.fw.dto.PageDto;
import com.fw.entity.Article;
import com.fw.entity.Category;
import com.fw.entity.ResponseResult;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.mapper.CategoryMapper;
import com.fw.service.ArticleService;
import com.fw.service.CategoryService;
import com.fw.utils.BeanCopyUtils;
import com.fw.utils.WebUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-06-04 14:58:41
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    ArticleService articleService;

    @Resource
    CategoryService categoryService;

    @Resource
    CategoryMapper categoryMapper;

    //只显示有文章的分类
    @Override
    public List<CategoryDto> getCategoryList1() {
        //筛选出状态正常的文章
        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(lambdaQueryWrapper);

        //获得他们的分类id
        Set<Long> collect = list.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        //使用分类id来查询状态正常的分类
        List<Category> categoryList=listByIds(collect);

        //筛选出状态正常的分类
        categoryList = categoryList.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        //转化成vo
        List<CategoryDto> categoryDtos = BeanCopyUtils.copyBeanList(categoryList, CategoryDto.class);
        return categoryDtos;
    }

    //状态正常的全部分类
    @Override
    public List<CategoryDto> getCategoryList() {
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Category::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Category> list = categoryService.list(lambdaQueryWrapper);

        //转化成vo
        List<CategoryDto> categoryDtos = BeanCopyUtils.copyBeanList(list, CategoryDto.class);
        return categoryDtos;
    }

    @Override
    public void export(HttpServletResponse response) {
        try {
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            List<Category> categoryList=list();

            List<CategoryExcelVo> categoryExcelVos = BeanCopyUtils.copyBeanList(categoryList, CategoryExcelVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).autoCloseStream(Boolean.FALSE)
                    .sheet("分类导出")
                    .doWrite(categoryExcelVos);

        } catch (IOException e) {
            e.printStackTrace();

            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));

        }
    }

    @Override
    public PageDto getAllList(int pageNum, int pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Category::getName,name)
                .like(status!=null,Category::getStatus,status);

        Page<Category> categoryPage=new Page<>(pageNum,pageSize);
        page(categoryPage,queryWrapper);

        PageDto pageDto=new PageDto(categoryPage.getRecords(),categoryPage.getTotal());
        return pageDto;
    }

    @Override
    public boolean addCategory(Category category) {
        return save(category);
    }

    @Override
    public Boolean updateOne(Category category) {
        return updateById(category);
    }

    @Override
    public Object delCategory(Long id) {
        return categoryMapper.UpdateDel_flag(id);
    }
}












