package com.lagou.service.impl;

import com.lagou.dao.MenuMapper;
import com.lagou.dao.RoleMapper;
import com.lagou.domain.Role;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    /*
   角色列表查询
    */
    public List<Role> findAllRole(Role role){

        List<Role> allRole = roleMapper.findAllRole(role);
        return allRole;


    }

    /*
   添加角色
    */
    @Override
    public void saveRole(Role role) {
        Date date = new Date();
        role.setCreatedTime(date);
        role.setUpdatedTime(date);
        role.setCreatedBy("system");
        role.setUpdatedBy("system");
        roleMapper.saveRole(role);
    }

    //根据id查询角色信息
    @Override
    public Role findRoleById(Integer id) {
        Role roleById = roleMapper.findRoleById(id);

        return roleById;
    }

    /*
修改角色
 */
    @Override
    public void updateRole(Role role) {
        role.setUpdatedTime(new Date());
        roleMapper.updateRole(role);
    }

    /*
    删除角色
     */
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public void deleteRole(Integer id) {
        //清空中间表关系
        menuMapper.deleteRoleContextMenu(id);

        roleMapper.deleteRole(id);

        //这个最好有顺序，因为我们通常需要先删除对应外键联系，然后删除主键
        //若没有外键关系，那么就可以直接删除主键
        //但是防止有外键联系及其数据的合理性
        //我们通常都会先删除对应外键或者有外键关系的字段（没有设置外键，但是当作外键看的字段）

    }
}
