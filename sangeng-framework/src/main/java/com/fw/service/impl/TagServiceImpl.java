package com.fw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.constant.SystemConstants;
import com.fw.dto.CategoryDto;
import com.fw.dto.PageDto;
import com.fw.dto.TagDto;
import com.fw.entity.Category;
import com.fw.entity.Tag;
import com.fw.mapper.TagMapper;
import com.fw.service.TagService;
import com.fw.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-07-13 15:46:31
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    TagMapper tagMapper;

    @Override
    public PageDto getTag(int pageNum, int pageSize, String name, String remark) {
        LambdaQueryWrapper<Tag> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Tag::getDelFlag,0)
                .eq(StringUtils.hasText(name),Tag::getName,name)
                .eq(StringUtils.hasText(remark),Tag::getName,remark);

        Page<Tag> TagPage=new Page<>(pageNum,pageSize);
        page(TagPage,lambdaQueryWrapper);

        PageDto pageDto= new PageDto(TagPage.getRecords(),TagPage.getTotal());

        return pageDto;
    }

    @Override
    public boolean addTag(Tag tag) {
        return save(tag);
    }

    //逻辑删除
    @Override
    public Integer del(Long id) {
        return tagMapper.UpdateDel_flag(id);
    }

    @Override
    public Tag selectOne(Long id) {
        return getById(id);
    }

    @Override
    public boolean updateOne(Tag tag) {
        Tag Newtag = getById(tag.getId());
        Newtag.setName(tag.getName());
        Newtag.setRemark(tag.getRemark());
        return updateById(Newtag);
    }

    @Override
    public List<TagDto> getAll() {
        List<TagDto> tagDtos = BeanCopyUtils.copyBeanList(list(), TagDto.class);
        return tagDtos;
    }
}
