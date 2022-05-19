package com.lagou.dao;

import com.lagou.domain.Menu;
import com.lagou.domain.Role_menu_relation;

import java.util.List;

/**
 *
 */
public interface MenuMapper {

    /*
    查询所有父子菜单信息
     */
    public List<Menu> findSubMenuListByPid(Integer pid);

 /*
    根据角色id查询该角色的菜单信息id
     */

    public List<Integer> findMenuByRoleId(Integer roleid);

    /*
    根据roleid清空中间表的对应关联关系
     */

    public void deleteRoleContextMenu(Integer roleid);
    /*
    为角色分配菜单
     */
    public void roleContextMenu(Role_menu_relation role_menu_relation);

    /*
    查询所有菜单列表
     */
    public List<Menu> findAllMenu();

    /*
  根据id查询菜单信息
   */
    public Menu findMenuById(Integer id);

    /*
    添加菜单
     */
    public void saveMenu(Menu menu);

    /*
    修改菜单
     */
    public void updateMenu(Menu menu);

    /*
    删除菜单
     */
    public void deleteMenu(Integer id);
}
