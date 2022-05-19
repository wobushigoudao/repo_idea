package com.lagou.dao;

import com.lagou.domain.Role;

import java.util.List;

/**
 *
 */
public interface RoleMapper {

    /*
    角色列表条件查询
     */
    public List<Role> findAllRole(Role role);

    /*
    添加角色
     */
    public void saveRole(Role role);

    /*
    回显角色信息(根据id查询角色信息）
     */
    public Role findRoleById(Integer id);

    /*
    修改角色
     */
    public void updateRole(Role role);

    /*
    删除角色
     */
    public void deleteRole(Integer id);


}
