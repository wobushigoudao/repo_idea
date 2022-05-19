package com.lagou.dao;

import com.lagou.domain.Resource;
import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResourceVo;

import java.util.List;

/**
 *
 */
public interface ResourceMapper {
    /*
    资源分页&多条件查询
     */
    public List<Resource> findAllResourceByPage(ResourceVo resourceVo);
    //如果resultType中的返回类型与Resource不一致，基本也不会报错，因为在运行期间，默认是Object了

    /*
    查询所有资源分类
     */
    public List<ResourceCategory> findAllResourceCategory();
}
