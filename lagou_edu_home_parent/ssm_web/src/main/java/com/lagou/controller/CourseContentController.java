package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/courseContent")
public class CourseContentController {


    @Autowired
    private CourseContentService courseContentService;

    //课程id查询课程内容
    //到这里发现，dao,service,controller的这些层，对应方法都是一样的
    //主要是为了方便区分，若使得方法多样，则不好维护（对看和找来说）
    @RequestMapping("/findSectionAndLesson") //这个配置有时只要符合意思即可，不需要与方法名一模一样
    public ResponseResult findSectionAndLessonByCourseId(Integer courseId){
        //调用service
        List<CourseSection> sectionAndLessonByCourseId = courseContentService.findSectionAndLessonByCourseId(courseId);

        ResponseResult responseResult = new ResponseResult(true, 200, "章节及课时内容查询成功", sectionAndLessonByCourseId);
        return responseResult;

    }

    /*
 回显章节对应的课程信息
  */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(Integer courseId){
        //调用service
        Course courseByCourseId = courseContentService.findCourseByCourseId(courseId);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询课程信息成功", courseByCourseId);
        return responseResult;
    }

     /*
    新建或修改章节信息
    与新增课程一样，添加和修改操作放在一起，通过判断id值
    来操作对应的sql执行，所以方法名是如下这样的
    而对应的代码修改，也是在后面进行修改扩展
    现在只操作新建章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection){

        ResponseResult responseResult;
        if(courseSection.getId() == null) {
            //调用service
            courseContentService.saveSection(courseSection);
            responseResult = new ResponseResult(true, 200, "新增章节成功", null);
        }else{
            //调用service
            courseContentService.updateSection(courseSection);
            responseResult = new ResponseResult(true, 200, "修改章节成功", null);
        }
        return responseResult;
    }

    /*
    修改章节状态
     */
    //很少的参数，一般就不需要创建对象了，节省一点创建对象所要的空间
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(int id, int status){
        //调用service
        courseContentService.updateSectionStatus(id, status);

        Map<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("status", status);
        ResponseResult responseResult = new ResponseResult(true, 200, "修改章节状态成功", objectObjectHashMap);
        return responseResult;
    }

     /*
   添加或修改课时信息
   也是添加课时信息和对应修改课时信息放一起
   在后面进行修改扩展
   现在只操作新建课时信息
    */
    @RequestMapping("/saveOrUpdateLesson")
    public ResponseResult saveOrUpdateLesson(@RequestBody CourseLesson courseLesson){
        ResponseResult responseResult;
        if(courseLesson.getId() == null) {
            courseContentService.saveLesson(courseLesson);

           responseResult = new ResponseResult(true, 200, "添加课时成功", null);
        }else{
            courseContentService.updateLesson(courseLesson);
           responseResult = new ResponseResult(true, 200, "修改课时成功", null);
        }
        return responseResult;
    }

      /*
  修改课时状态
   */
    @RequestMapping("/updateLessonStatus")
    public ResponseResult updateLessonStatus(@RequestBody CourseLesson courseLesson){
        courseContentService.updateLessonStatus(courseLesson);
        ResponseResult responseResult = new ResponseResult(true, 200, "修改课时状态成功", null);
        return responseResult;
    }



}
