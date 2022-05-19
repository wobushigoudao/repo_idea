package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.MulticastChannel;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    /*
  多条件课程列表查询

   */
    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVo courseVo) {
        //之所以使用RequestBody，是因为传递过来的参数是json格式
        //调用service

        List<Course> courseByCondition = courseService.findCourseByCondition(courseVo);

        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", courseByCondition);

        return responseResult;

    }

    /*
    课程图片上传
     */
    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //判断接收到的上传文件是否为空
        //file.isEmpty()判断对应的数据是否为空
        if (file.isEmpty()) {
            throw new RuntimeException();
            //主动抛出异常，而不用等出现异常后执行对应异常信息（如try）
            //当然使用这个方法时也是需要对应处理异常的
        }

        //获取项目部署路径
        //D:\apache-tomcat-8.5.56\webapps\ssm_web\
        String realPath = request.getServletContext().getRealPath("/");
        //D:\apache-tomcat-8.5.56\webapps\
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));

        //获取原文件名
        //如lagou.jpg
        String originalFilename = file.getOriginalFilename();
        //让文件名基本不重复，一个用户基本不可能会有相同的名称
        //多个用户可能会有，但是用户之间肯定是不可互相访问的，所以这里就相当于设置了文件名不重复
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //得到后缀名，用时间戳当名称
        //如1651975001568.jpg

        //开始文件上传
        // D:\apache-tomcat-8.5.56\webapps\ upload\
        //注意：注释里面当出现\ u时，必须要有数，否则报错，所以上面我在\和u之间加了一个空格
        //\ u是转义字符，表示后面跟一个十六进制数,通过这个十六进制数来指定一个字符，若不是则报错
        String uploadPath = substring + "upload\\";
        //加上/也可以，但为了符号windows的目录化，就使用\\了，网页就是/
        File file1 = new File(uploadPath, newFileName);

        //如果目录不存在则创建目录（判断有没有父路径uploadPath，没有则创建）
        if (!file1.getParentFile().exists()) {
            file1.getParentFile().mkdirs();
            System.out.println("创建目录" + file1);
        }

        //图片进行上传
        file.transferTo(file1);

        //将文件名和文件路径返回给前端，使得前端显示图片
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("fileName", newFileName);
        //文件名，如1651975001568.jpg
        objectObjectHashMap.put("filePath", "http://localhost:8080/upload/" + newFileName);
        //在服务器上的访问路径，也就是通过端口访问服务器资源的路径

        ResponseResult responseResult = new ResponseResult(true, 200, "图片上传成功", objectObjectHashMap);

        return responseResult; //会解析集合的，也就是将map进行解析


        //这里我说明一下：json实际上就是一种格式
        //怎么说呢，就是我们规定字符串以什么形式存在
        //如有[]包括，里面用大括号，所以可以说，字符串的一系列变化，就是json格式
        //那么json格式可以存在多个形式，只是使用json格式时
        //有很多工具，就是将对应信息解析成数据，用特定格式的字符串存好
        //如数值，字符串，普通集合，map集合，类，json工具通过解析他们，进行得到对应字符串使得拼接起来
        //如普通集合list里面有数值1，字符串1，类（或者集合），那么结果是
        //先解析普通集合list，给出[]
        //数值1，给出1，那么结果是[1]
        //字符串1，给出"1"(java里面会转义),即结果[1,"1"]，不同集合数之间会加上,
        //类，给出{"key":value},即结果是[1,"1",{"key":value}]
        //map集合，给出{"key":value}，即结果是[1,"1",{"key":value},{"key":value}]
        //可以发现，对应json就是通过解析，来操作字符串，使得返回的
        //注意：什么的key是由""包括的，也就是说，无论key是什么类型，都被""包括，这是json的解析方式
        //当然，对应map集合本身来说，是有对应覆盖的，如key相同，那么他只有对应一个相同的那个key
        //然后作为响应数据，请求数据我们一般之间编写这样的字符串（json）的
    }

    /*
    新增或修改课程信息及其讲师信息
    新增课程信息和修改课程信息要写在同一个方法中
     */
    @RequestMapping("saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {

        ResponseResult responseResult;
        if(courseVo.getId() == 0) {
            //新增课程
            //调用service
            courseService.saveCourseOrTeacher(courseVo);

            responseResult = new ResponseResult(true, 200, "新增成功", null);
        }else{
            //更新课程
            //调用service
            courseService.updateCourseOrTeacher(courseVo);

            responseResult = new ResponseResult(true, 200, "修改成功", null);


        }
        return responseResult;

    }

    //根据id查询课程信息（包括讲师信息）
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id){
        CourseVo courseById = courseService.findCourseById(id);
        ResponseResult responseResult = new ResponseResult(true,200,"根据id查询课程信息成功",courseById);
        return responseResult;
    }

    /*
    课程状态管理
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id ,Integer status){

        //调用service，传递参数
        courseService.updateCourseStatus(id,status);

        //响应结果
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("status",status);

        ResponseResult responseResult = new ResponseResult(true, 200, "课程状态变更成功", objectObjectHashMap);
        return responseResult;
    }

}
