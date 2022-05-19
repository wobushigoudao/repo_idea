package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

/**
 *
 */
public interface UserMapper {
    /*
    用户分页以及多条件组合查询
     */
    public List<User> findAllUserByPage(UserVo userVo);
    /*
    用户状态修改
     */
    public void updateUserStatus(User user);

    /*
    用户登录（根据用户名查询具体用户信息，就是将查询的与密码进行对应）
     */
    public User login(User user);

    /*
    根据用户id查询关联的角色信息
     */
    public List<Role> findUserRelationRoleById(Integer id);

    /*
    根据用户id清空中间表的对应关联关系
     */

    public void deleteUserContextRole(Integer id);
    /*
    分配角色（即添加对应用户和角色的关联关系）
     */
    public void userContextRole(User_Role_relation user_role_relation);

    /*
    根据角色id，查询角色所拥有的顶级菜单（-1），由于角色不止一个，那么权限也就需要合并
    所以参数是list集合，用来传递多个角色id，使得合并权限，即一般sql语句使用in操作
    角色对应了多个菜单
     */
    public List<Menu> findParentMenuByRoleId(List<Integer> ids);
    //前面的findUserRelationRoleById方法可以通过用户id获得对应的角色信息，即可以有List<Integer> ids出现

    /*
    根据Pid（父菜单id），查询子菜单信息
     */
    public List<Menu> findSubMenuByPid(Integer pid);

    /*
    获取用户拥有的资源信息（实际上就是获取角色拥有的资源信息，但因为关联，所以也可说明是用户拥有）2
     */
    public List<Resource> findResponseByRoleId(List<Integer> ids);

     public List<Resource> findResponseByRole(List<Integer> ids);

     public void text1();
     public void text2();
     public void text3();
     public void text345();
     public void text4();
     public void text5();
     public void text6();
     public void text7();
     public void text8();
}
