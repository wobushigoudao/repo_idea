package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.RoleMenuVo;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /*
查询所有父子菜单信息
 */
    //写在角色模块的Controller里面也可以，因为这个操作也是属于角色模块的
    // 但这里我们根据表来决定对应的层级（如dao，service，controller）
    // 但最好还是根据接口文档来编写，这里就不根据接口文档编写了（最好不要这样）
    @RequestMapping("/findAllMenu")
    public ResponseResult findSubMenuListByPid() {
        //手动的设置查询父菜单，且基本是必须设置-1的，因为表就是这样，当然，你也可以进行参数的传递
        //但这个-1是基本不会变化的，所以也就没必要了
        //所以可以直接写-1，而不需要传递参数
        List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1); //-1与父菜单关联

        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("parentMenuList", subMenuListByPid);
        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有的父子菜单信息成功", objectObjectHashMap);
        return responseResult;
    }

    /*
  根据角色id查询该角色的菜单信息id
   */
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId) {

        List<Integer> menuByRoleId = menuService.findMenuByRoleId(roleId);
        ResponseResult responseResult = new ResponseResult(true, 200, "根据角色id查询菜单信息成功", menuByRoleId);
        return responseResult;
    }

    /*
    为角色分配菜单
     */

    @RequestMapping("/roleContextMenu")
    public ResponseResult roleContextMenu(@RequestBody RoleMenuVo roleMenuVo) {

        menuService.roleContextMenu(roleMenuVo);

        ResponseResult responseResult = new ResponseResult(true, 200, "角色分配菜单成功", null);
        return responseResult;
    }

    /*
    查询所有菜单列表
     */
    @RequestMapping("/findAllMenuList")
    public ResponseResult findAllMenu() {
        List<Menu> allMenu = menuService.findAllMenu();
        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有菜单信息成功", allMenu);
        return responseResult;
    }

    /*
    回显菜单信息，父菜单信息或者本身的所有菜单信息
     */
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id) {

        ResponseResult responseResult;
        HashMap<Object, Object> objectObjectHashMap;
        List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);
        //这里不可以使用id当作参数，主要是对应判断不与这个参数有联系，只是用来确定添加和修改操作
        //所有对应修改操作里，也会使用这个方法，即不能使用id当作参数

        //判断id的值是否是-1，-1则是添加操作回显，否则是修改操作回显，用这种形式来决定添加和修改的回显不同
        if (id == -1) {

            //添加 回显信息中只需要查询menu所有父菜单信息

            objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("menuInfo", null);
            objectObjectHashMap.put("parentMenuList", subMenuListByPid);
            responseResult = new ResponseResult(true, 200, "添加菜单信息回显成功", objectObjectHashMap);
        } else {
            //修改操作 回显所有的menu信息，即所有字段信息（包括父菜单信息和普通的字段信息，也就是所有的信息）
            Menu menu = menuService.findMenuById(id);
            objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("menuInfo", menu);
            objectObjectHashMap.put("parentMenuList", subMenuListByPid);
            responseResult = new ResponseResult(true, 200, "修改菜单信息回显成功", objectObjectHashMap);


        }

        return responseResult;
    }

    /*
    添加菜单（后续会进行修改扩展）
     */
    @RequestMapping("/saveOrUpdateMenu")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menu){
        ResponseResult responseResult;
        if(menu.getId() == null){
            menuService.saveMenu(menu);

           responseResult = new ResponseResult(true, 200, "添加菜单成功", null);

        }else{
            menuService.updateMenu(menu);
            responseResult = new ResponseResult(true, 200, "修改菜单成功", null);

        }
        return responseResult;
    }

    /*
    删除菜单
     */
    @RequestMapping("/deleteMenu")
    public ResponseResult deleteMenu(Integer id){
        menuService.deleteMenu(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "删除菜单成功", null);
        return responseResult;
    }
}
