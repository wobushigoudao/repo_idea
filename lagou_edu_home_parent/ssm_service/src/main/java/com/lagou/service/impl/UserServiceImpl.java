package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    //用户分页及其多条件查询
    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {

        //分页设置
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());

        List<User> allUserByPage = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<>(allUserByPage);
        return pageInfo;
    }
    /*
       用户状态修改
        */
    @Override
    public void updateUserStatus(Integer id,String status) {

        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdate_time(new Date());

        userMapper.updateUserStatus(user);


    }

    /*
    用户登录
     */
    @Override
    public User login(User user) throws Exception {
        User pass = userMapper.login(user);

        //判断用户是否存在，以及对应的密码是否正确，只要有一个没有满足，那么就返回null
        //表示用户名不存在或者密码错误（用户名或密码错误）
        if(pass!=null && Md5.verify(user.getPassword(),"lagou",pass.getPassword())){
            return pass;
        }else {
            return null;

        }
    }

    /*
分配角色中查询用户关联的角色
 */
    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        List<Role> userRelationRoleById = userMapper.findUserRelationRoleById(id);

        return userRelationRoleById;
    }

    /*
  分配角色（即添加对应用户和角色的关联关系），包含删除对应用户与角色的关联
   */
    @Override
    public void userContextRole(UserRoleVo userRoleVo) {
        //根据用户id清空中间表关系
        userMapper.deleteUserContextRole(userRoleVo.getUserId());

        for(Integer roleId:userRoleVo.getRoleIdList()){
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userRoleVo.getUserId());
            user_role_relation.setRoleId(roleId);
            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedBy("system");

            userMapper.userContextRole(user_role_relation);
        }
    }

    /*
   获取用户权限（有四个方法的调用，其中一个是复用的，其他三个是刚刚编写的方法）
   这里我们直接封装到ResponseResult里面了
   当然你也可以在Controller层里进行操作
    */
    @Override
    //会取出用户id当成参数
    public ResponseResult getUserPermissions(Integer userid) {
        //获取当前用户拥有的角色
        List<Role> userRelationRoleById = userMapper.findUserRelationRoleById(userid);

        //获取角色id保存到list集合中
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (Role role : userRelationRoleById) {
            roleIds.add(role.getId());
        }

        //根据角色id获取父菜单权限
        List<Menu> parentMenuByRoleId = userMapper.findParentMenuByRoleId(roleIds);

        //查询父菜单关联的子菜单
        for(Menu menu:parentMenuByRoleId){
            List<Menu> subMenuByPid = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenuByPid);
        }

        //获取资源信息
        List<Resource> responseByRoleId = userMapper.findResponseByRoleId(roleIds);

        //封装数据并返回
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("menuList",parentMenuByRoleId);
        hashMap.put("resourceList",responseByRoleId);

        //这里直接在Service层将数据封装到ResponseResult里面了
        ResponseResult responseResult = new ResponseResult(true, 200, "获取用户权限信息成功",
                hashMap);

        return responseResult;
    }
}
