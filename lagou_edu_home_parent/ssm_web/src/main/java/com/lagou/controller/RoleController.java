package com.lagou.controller;

import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //角色列表条件查询
    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role) {
        List<Role> allRole = roleService.findAllRole(role);
        ResponseResult responseResult = new ResponseResult(true, 200, "查询角色成功", allRole);
        return responseResult;
    }

    /*
    添加角色，后面会进行修改角色扩展
     */
    @RequestMapping("/saveOrUpdateRole")
    public ResponseResult saveOrUpdateRole(@RequestBody Role role) {
        ResponseResult responseResult;
        if(role.getId() == null) {

            roleService.saveRole(role);
            responseResult = new ResponseResult(true, 200, "添加角色成功", null);

        }else{

            roleService.updateRole(role);
            responseResult = new ResponseResult(true, 200, "修改角色成功", null);

        }
        return responseResult;
    }

    /*
    根据id查询角色信息
     */
    @RequestMapping("/findRoleById")
    public ResponseResult findRoleById(Integer id) {
        Role roleById = roleService.findRoleById(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "根据id查询角色成功", roleById);
        return responseResult;
    }

    /*
    删除角色
     */
    @RequestMapping("/deleteRole")
    //要注意：虽然对应整型可以识别01，02等类型的数据，实际上就是1，2
    //但使用json格式时，会被检查，也就是说当前端传递的json格式的数是01，02等整型类型时
    //服务器会处理不了这个json，即400报错，请求参数错误
    public ResponseResult deleteRole(Integer id) {
        roleService.deleteRole(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "删除角色成功", null);
        return responseResult;
    }
}