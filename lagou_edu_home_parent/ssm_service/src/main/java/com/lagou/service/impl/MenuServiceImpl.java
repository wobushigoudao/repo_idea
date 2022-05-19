package com.lagou.service.impl;

import com.lagou.dao.MenuMapper;
import com.lagou.domain.Menu;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;
import com.lagou.domain.Role_menu_relation;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /*
 查询所有父子菜单信息
  */
    @Override
    public List<Menu> findSubMenuListByPid(Integer pid) {
        List<Menu> subMenuListByPid = menuMapper.findSubMenuListByPid(pid);

        return subMenuListByPid;
    }

    /*
  根据角色id查询该角色的菜单信息id
   */
    @Override
    public List<Integer> findMenuByRoleId(Integer roleid) {
        List<Integer> menuByRoleId = menuMapper.findMenuByRoleId(roleid);

        return menuByRoleId;
    }

    /*
    为角色分配菜单，包含删除对应关联，和添加对应关联
    这里就体现出使用Service层的作用了
     */
    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {

        //清空中间表的关系
        menuMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());

        //遍历添加关联
        for (Integer menuId : roleMenuVo.getMenuIdList()) {
            Role_menu_relation role = new Role_menu_relation();
            role.setMenuId(menuId);
            role.setRoleId(roleMenuVo.getRoleId());
            Date date = new Date();
            role.setCreatedTime(date);
            role.setUpdatedTime(date);
            role.setCreatedBy("system");
            role.setUpdatedBy("system");
            menuMapper.roleContextMenu(role);
        }

        //这里体现出了使用service层的主要好处（主要的就是简便controller层的编写）
        //若不使用这个service层，那么就需要在controller层自己去调用dao层的方法
        //只有一个dao层方法那么还算好的，若有很多的dao层联系的方法，那么controller层的代码会非常的多，不好维护

    }

    /*
 查询所有菜单列表
  */
    @Override
    public List<Menu> findAllMenu() {
        List<Menu> allMenu = menuMapper.findAllMenu();
        return allMenu;
    }

    /*
    根据id查询菜单信息
     */
    @Override
    public Menu findMenuById(Integer id) {
        Menu menuById = menuMapper.findMenuById(id);
        return menuById;
    }

    /*
   添加菜单
    */
    @Override
    public void saveMenu(Menu menu) {
        Date date = new Date();
        menu.setCreatedTime(date);
        menu.setUpdatedTime(date);
        menuMapper.saveMenu(menu);

    }

    /*
  修改菜单
   */
    @Override
    public void updateMenu(Menu menu) {
        menu.setUpdatedTime(new Date());
        menuMapper.updateMenu(menu);
    }

    /*
   删除菜单
    */
    @Override
    public void deleteMenu(Integer id) {
        menuMapper.deleteMenu(id);

    }
}
