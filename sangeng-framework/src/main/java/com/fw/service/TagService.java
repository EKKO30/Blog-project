package com.fw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fw.dto.PageDto;
import com.fw.dto.TagDto;
import com.fw.entity.Tag;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-07-13 15:46:31
 */
public interface TagService extends IService<Tag> {

    PageDto getTag(int pageNum, int pageSize, String name, String remark);

    boolean addTag(Tag tag);

    Integer del(Long id);

    Tag selectOne(Long id);

    boolean updateOne(Tag tag);

    List<TagDto> getAll();
}
