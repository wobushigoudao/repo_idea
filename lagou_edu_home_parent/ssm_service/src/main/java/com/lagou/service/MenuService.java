package com.lagou.service;

import com.lagou.domain.Menu;
import com.lagou.domain.RoleMenuVo;
import com.lagou.domain.Role_menu_relation;

import java.util.List;

/**
 *
 */
public interface MenuService {

    /*
  查询所有父子菜单信息
   */
    public List<Menu> findSubMenuListByPid(Integer pid);

    /*
    根据角色id查询该角色的菜单信息id
     */

    public List<Integer> findMenuByRoleId(Integer roleid);

    /*
    为角色分配菜单，包含删除对应关联，和添加对应关联
    这里就体现出使用Service层的作用了
     */
    public void roleContextMenu(RoleMenuVo roleMenuVo);

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
