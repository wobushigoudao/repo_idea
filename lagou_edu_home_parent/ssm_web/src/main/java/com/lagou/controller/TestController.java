package com.lagou.controller;

import com.lagou.domain.Test;
import com.lagou.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController //相当与在类上加上@Controller
//在所有方法上加上@ResponseBody
//现在补充一点，前面说过@ResponseBody操作集合变json
//实际上是操作所有对象，包括集合，都变成json
//而字符串就是字符串，也包括字符串数组，不会变成json
//这是他的底层作用，所以也可以说，@ResponseBody就是操作对象，变成json
//实际上@RestController就相当于在类上面加上@Controller和@ResponseBody
//而@ResponseBody在类上面就相当于给该类的所有方法加上@ResponseBody
//之所以这样可以点进去看看他的结构（ctrl+鼠标左键），可以发现有上面两个注解修饰
//所有在扫描到这个注解时，获取内容时，也会操作他们两个注解的操作
//所以说，操作注解时，未必只操作他的内容，也会操作他里面注解
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/findAllTest")
    public List<Test> findAllTest(){

        List<Test> allTest = testService.findAllTest();
        return allTest;
    }
}

