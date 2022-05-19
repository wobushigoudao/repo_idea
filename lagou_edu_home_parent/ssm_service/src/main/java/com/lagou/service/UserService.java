package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.*;

import java.util.List;

/**
 *
 */
public interface UserService {
    /*
    用户分页及其多条件查询
     */

    public PageInfo findAllUserByPage(UserVo userVo);
    //返回值类型在前面说过，即包含了分页所需的信息，也包含了对应的查询结果信息（通常是list集合）

    /*
   用户状态修改
    */
    public void updateUserStatus(Integer id,String status);

    /*
    用户登录（根据用户名查询具体用户信息，就是将查询的与密码进行对应）
     */
    public User login(User user) throws Exception;

    /*
    分配角色中查询用户关联的角色
     */
    public List<Role> findUserRelationRoleById(Integer id);

    /*
   分配角色（即添加对应用户和角色的关联关系），包含删除对应用户与角色的关联
    */
    public void userContextRole(UserRoleVo userRoleVo);

    /*
    获取用户权限（有四个方法的调用，其中一个是复用的，其他三个是刚刚编写的方法）
    这里我们直接在Service层里将数据封装到ResponseResult里面了
    当然你也可以在Controller层里进行操作封装
    将我们操作的map集合返回就可以了
    封装：可以说是对应有参构造或者set方法，或者其他方法，使得可以赋值的操作，都可以叫做封装

     */
    public ResponseResult getUserPermissions(Integer userid);
    //会取出用户id当成参数
}
