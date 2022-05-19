package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResourceVo;

import java.util.List;

/**
 *
 */
public interface ResourceService {
    /*
   资源分页&多条件查询
    */
    public PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo);

    /*
  查询所有资源分类
   */
    public List<ResourceCategory> findAllResourceCategory();
}
