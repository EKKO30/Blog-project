package com.ad.Controller;

import com.fw.entity.Article;
import com.fw.entity.Menu;
import com.fw.entity.ResponseResult;
import com.fw.entity.Tag;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.service.ArticleService;
import com.fw.service.CategoryService;
import com.fw.service.MenuService;
import com.fw.service.TagService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Resource
    TagService tagService;

    @Resource
    CategoryService categoryService;

    @Resource
    ArticleService articleService;

    @Resource
    MenuService menuService;


    @GetMapping("/tag/list")
    public ResponseResult  gettag(int pageNum,int pageSize,String name,String remark){
        return ResponseResult.okResult(tagService.getTag(pageNum,pageSize,name,remark));
    }

    //增加
    @PostMapping("/tag")
    public ResponseResult addTag(@RequestBody Tag tag){
        boolean i=tagService.addTag(tag);
        if(i){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(222,"添加失败");
        }
    }

    //删除tag
    @DeleteMapping("/tag/{id}")
    public ResponseResult DeleteTag(@PathVariable Long id){
        int i=tagService.del(id);
        if(i == 1){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(333,"删除失败");
        }
    }

    //查询一个
    @GetMapping("/tag/{id}")
    public ResponseResult getOne(@PathVariable Long id){
        Tag tag=tagService.selectOne(id);
        if(tag != null ){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    //修改
    @PutMapping("/tag")
    public ResponseResult update(@RequestBody Tag tag){
        boolean i=tagService.updateOne(tag);
        if(i){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(444,"更新失败");
        }
    }

    //用于写博文的查询所有分类
    @GetMapping("/category/listAllCategory")
    public ResponseResult listAllCategory(){
        return ResponseResult.okResult(categoryService.getCategoryList());
    }

    //用于写博文的查询所有标签
    @GetMapping("/category/listAllTag")
    public ResponseResult listAllTag(){
        return ResponseResult.okResult(tagService.getAll());
    }

    //写博文
    @PostMapping("/article")
    public ResponseResult article(@RequestBody Article article){
        return ResponseResult.okResult(articleService.addArticle(article));
    }

    //excel形式导出数据
    @PreAuthorize("@ps.haspermission('content:category:export')")
    @GetMapping("/category/export")
    public void categoryExport(HttpServletResponse response){
        categoryService.export(response);
    }

    @GetMapping("/article/list")
    public ResponseResult articleList(int pageNum,int pageSize,String title,String summary){
        return ResponseResult.okResult(articleService.getByCondition(pageNum,pageSize,title,summary));
    }

    @PutMapping("/article")
    public ResponseResult articleList(Article article){
        return ResponseResult.okResult(articleService.updateArticle(article));
    }

    @DeleteMapping("/article/{id}")
    public ResponseResult articledel(@PathVariable Long id){
        if(articleService.delArticle(id)==1){
            return ResponseResult.okResult();
        }

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    //添加菜单
    @PostMapping("/addMenu")
    public ResponseResult addMenu(@RequestBody Menu menu){
        menuService.addMenu(menu);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/menu/{id}")
    public ResponseResult delMenu(@PathVariable Long id){

        if(menuService.delMenu(id) == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }else {
            return ResponseResult.okResult();
        }
    }
}
























