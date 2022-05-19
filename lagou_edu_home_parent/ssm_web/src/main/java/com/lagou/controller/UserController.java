package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*
    用户分页及其多条件查询
     */
    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo) {

        PageInfo allUserByPage = userService.findAllUserByPage(userVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "用户信息分页多条件查询成功", allUserByPage);
        return responseResult;
    }

    /*
    用户状态修改
     */
    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(Integer id,String status) {
        userService.updateUserStatus(id,status);
        ResponseResult responseResult = new ResponseResult(true, 200, "用户状态修改成功",status);
        return responseResult;
    }

    /*
   用户登录
    */
    @RequestMapping("/login")
    //前端是get请求，那么这里就不使用RequestBody了，直接默认封装
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {
        ResponseResult responseResult;
        User login = userService.login(user);
        if(login!=null){
            //保存用户id（不是用户名，是他们的唯一标识id，这里的用户名就是手机号）和access_token到session中

            HttpSession session = request.getSession();
            String access_token = UUID.randomUUID().toString();
            //对应的access_token的value值通常是不一致
            //若一致也没关系，反正会覆盖掉之前的access_token的value值
            //但会对权限的判断出现影响（一般用这个做权限判断）
            //但不一致可以通过UUID来知道一些信息
            //如你登录的当前日期和时间（可能可以知道，可以进行操作，这里就不进行操作了）
            session.setAttribute("access_token",access_token);
            session.setAttribute("User_id",login.getId());
            //之所以存放这两个数据，其中token是为了进行权限的判断，先保存好
            //使得别人获取权限时，要与这个进行比较，而之所以不比较id，是防止id泄露
            //因为这个id是属于数据库的主键字段
            //一般我们都在除了必须要输入的数据是数据库的数据外（如登录操作），都不会显示出对应数据库的数据
            //而上面两个的存放都可以进行免登录，一般不会试验id进行，而是token进行，防止泄露
            //这样，当登录一次后，下次登录，可以通过session来进行免登录，这样的操作先不编写
            //如下次来时
            //判断对应id（也有可能不是id，如token）是否一样（实际上基本是一样的，主要是存不存在，即是否是null）
            //使得直接登录成功
            //而之所以存放id，是为了在判断成功后，在进行用户权限所对应的菜单获取


            //将结果响应给前台
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("access_token",access_token);
            objectObjectHashMap.put("User_id",login.getId());
            //上面可以进行对应的回显操作，还有其他操作，这里就不说明了
            //而之所以需要进行封装到响应数据里，首先token是需要判断是否一致的，之所以不从服务器里获得
            //是为了再次进行判断，防止特殊情况
            //而id可以进行对应的回显，或者其他的方法调用


            //将查询出来的user，也存到map集合中（这里是login变量）
            //主要是为了使得用户登出时用到，到时候，就不可以免登录了
            //如判断他的id是否与对应session存的id一样
            //基本是一样的，主要是存不存在，即是否是null
            //使得删掉对应key，那么免登录就不可以了
            //当然也可以操作回显的id
            objectObjectHashMap.put("user",login);

            //注意：无论什么情况，都只是为了方便操作，若你认为上面的操作不合理，可以进行修改
            //实际上写上去也是没有什么坏处的


            responseResult = new ResponseResult(true, 1, "用户登录成功",objectObjectHashMap);
        }else{

            //400表示错误的请求，这里我们直接给出这个信息，实际上并没有错误，这时人为的
            responseResult = new ResponseResult(false, 400, "用户名或密码错误", null);


        }
        return responseResult;

    }

     /*
分配角色中查询用户关联的角色
 */
    @RequestMapping("/findUserRelationRoleById")
    public ResponseResult findUserRelationRoleById(Integer id){
        List<Role> userRelationRoleById = userService.findUserRelationRoleById(id);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询用户关联角色成功", userRelationRoleById);
        return responseResult;
    }

    /*
 分配角色（即添加对应用户和角色的关联关系），包含删除对应用户与角色的关联
  */
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserRoleVo userRoleVo){
        userService.userContextRole(userRoleVo);

        ResponseResult responseResult = new ResponseResult(true, 200, "分配角色成功", null);
        return responseResult;
    }

    /*
    获取用户权限，进行菜单动态展示
     */
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request){

        //获取请求头中的token（登录中进行回显的对应token）
        String header_token = request.getHeader("Authorization");

        System.out.println(header_token);
        //获取session中的token
        String access_token = (String) request.getSession().getAttribute("access_token");
        System.out.println(access_token);
        //判断token是否一致
        ResponseResult responseResult;
        if(header_token.equals(access_token)){

            //获取用户id
            Integer user_id = (Integer) request.getSession().getAttribute("User_id");
            responseResult = userService.getUserPermissions(user_id);

        }else{
            responseResult = new ResponseResult(false, 400, "获取菜单信息失败", null);

        }
        return responseResult;

    }
}
