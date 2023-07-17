package com.fw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fw.dto.LinkDto;
import com.fw.dto.PageDto;
import com.fw.entity.Category;
import com.fw.entity.Link;

import java.util.List;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-06-08 16:53:51
 */
public interface LinkService extends IService<Link> {

    List<LinkDto> getAllLink();

    PageDto getAllList(int pageNum, int pageSize, String name, String status);

    boolean addLink(Link link);

    Boolean updateOne(Link link);

    Object delLink(Long id);

}
