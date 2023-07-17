package com.fw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.constant.SystemConstants;
import com.fw.dto.LinkDto;
import com.fw.dto.PageDto;
import com.fw.entity.Category;
import com.fw.entity.Link;
import com.fw.mapper.LinkMapper;
import com.fw.service.LinkService;
import com.fw.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-06-08 16:53:52
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Resource
    LinkMapper linkMapper;

    //获得友联
    @Override
    public List<LinkDto> getAllLink() {
        LambdaQueryWrapper<Link> lambdaQueryWrapper=new LambdaQueryWrapper<Link>();
        //选择审核通过的友链
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(lambdaQueryWrapper);

        return BeanCopyUtils.copyBeanList(list, LinkDto.class);
    }

    @Override
    public PageDto getAllList(int pageNum, int pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Link::getName,name)
                .like(status!=null,Link::getStatus,status);

        Page<Link> linkPage=new Page<>(pageNum,pageSize);
        page(linkPage,queryWrapper);

        PageDto pageDto=new PageDto(linkPage.getRecords(),linkPage .getTotal());
        return pageDto;
    }

    @Override
    public boolean addLink(Link link) {
        return save(link);
    }

    @Override
    public Boolean updateOne(Link link) {
        return updateById(link);
    }

    @Override
    public Object delLink(Long id) {
        return linkMapper.UpdateDel_flag(id);
    }
}














