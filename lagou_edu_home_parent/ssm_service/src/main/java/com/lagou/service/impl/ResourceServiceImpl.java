package com.lagou.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.ResourceMapper;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResourceVo;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    /*
  资源分页&多条件查询
   */
    @Override
    public PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo) {

        //分页查询，参数一：当前页，参数二：每页显示条数
        PageHelper.startPage(resourceVo.getCurrentPage(), resourceVo.getPageSize());


        List<Resource> allResourceByPage = resourceMapper.findAllResourceByPage(resourceVo);


        PageInfo<Resource> resourcePageInfo = new PageInfo<>(allResourceByPage);

        return resourcePageInfo;
    }

    /*
查询所有资源分类
*/
    @Override
    public List<ResourceCategory> findAllResourceCategory() {
        List<ResourceCategory> allResourceCategory = resourceMapper.findAllResourceCategory();
        return allResourceCategory;
    }
}
