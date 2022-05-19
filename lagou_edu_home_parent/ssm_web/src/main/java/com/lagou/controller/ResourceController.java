package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResourceVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /*
  资源分页&多条件查询
   */
@RequestMapping("/findAllResourceByPage")
    public ResponseResult findAllResourceByPage(@RequestBody ResourceVo resourceVo) {
        PageInfo<Resource> allResourceByPage = resourceService.findAllResourceByPage(resourceVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "资源信息分页多条件查询成功", allResourceByPage);

        return responseResult;
    }

    /*
 查询所有资源分类
  */
    @RequestMapping("/findAllResourceCategory")
    public ResponseResult findAllResourceCategory(){
        List<ResourceCategory> allResourceCategory = resourceService.findAllResourceCategory();
        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有资源分类信息成功", allResourceCategory);
        return responseResult;
    }
}
