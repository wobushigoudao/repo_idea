package com.lagou.service.impl;

import com.lagou.dao.CourseMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.Teacher;
import com.lagou.service.CourseService;


import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;


/**
 *
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    /*
  多条件课程列表查询

   */
    @Override
    public List<Course> findCourseByCondition(CourseVo courseVo) {

        List<Course> courseByCondition = courseMapper.findCourseByCondition(courseVo);

        return courseByCondition;
    }

    /*
    添加课程及讲师信息
     */
    @Override
    public void saveCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        //封装课程信息
        Course course = new Course();

        //将对应参数的值赋值给course，只操作set赋值（首字母大小写忽略，即不看变量，判断set名称符合来进行赋值）
        //在这个项目中也有一个对应包的类名与这个是一样的，即org.springframework.beans.BeanUtils
        //但操作不同，这里用的是org.apache.commons.beanutils.BeanUtils
        //不要导错了，否则的话，没有进行赋值操作，虽然类名一样，但操作不一样
        //且由于类名相同，那么没有导入的类，不可再次导入了，否则报错，且没有导入的那个类使用时，是直接使用全路径名来操作的
        BeanUtils.copyProperties(course, courseVo);
        //注意：这个方法会检查Date的值是否是null，若是则报错，虽然我们设置Date时可以设置null
        //但他这个方法会判断的，即不准让Date出现null


        //补全课程信息，及创建时间（更新时间开始与创建时间一样）
        Date date = new Date();

        course.setCreateTime(date);
        course.setUpdateTime(date);

        //保存课程
        courseMapper.saveCourse(course);

        //获取新插入的id值
        int id = course.getId();

        //封装讲师信息
        Teacher teacher = new Teacher();

        BeanUtils.copyProperties(teacher, courseVo);

        //补全讲师信息
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setIsDel(0);
        teacher.setCourseId(id);

        //保存讲师信息
        courseMapper.saveTeacher(teacher);



    }


    //根据id查询课程信息（包括讲师信息）
    @Override
    public CourseVo findCourseById(Integer id) {
        CourseVo courseById = courseMapper.findCourseById(id);
        return courseById;
    }

    /*
    更新课程及讲师信息
     */
    @Override
    public void updateCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {

        //封装课程信息
        Course course = new Course();

        BeanUtils.copyProperties(course, courseVo);

        //补全信息
        Date date = new Date();
        course.setUpdateTime(date);

        //更新课程信息
        courseMapper.updateCourse(course);

        //封装讲师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher, courseVo);

        //补全信息
        teacher.setCourseId(course.getId());
        teacher.setUpdateTime(date);

        //更新讲师信息
        courseMapper.updateTeacher(teacher);

        //那么到这里我们可以发现，一个CourseVo基本封装了课程及其讲师的变量
        //使得我们只要操作这个类进行赋值就基本可以操作对应类的值了
        //但由于有些是需要后台进行封装的，如课程里的讲师外键（因为前端基本只会传递一个主体的主键）
        //或者时间的更新等等，那么由这种可以被随时改变的，我们通常放在后端操作，因为若给前端操作的话
        //不止前端需要多个参数，后端也需要多个参数，那么本来一个方法可以解决的，使得前后端都进行参数的添加了
        //所以可以有本来的参数就可以得到的，一般都在后端获得，如teacher.setCourseId(course.getId())
        //或者创建或者更新时间的方法：teacher.setCreateTime(date)，teacher.setUpdateTime(date)等等
    }

    /*
    课程状态变更
     */
    @Override
    public void updateCourseStatus(int id, int status) {

        //封装数据
        Course course = new Course();
        course.setId(id);
        course.setStatus(status);
        course.setUpdateTime(new Date());

        //调用mapper
        courseMapper.updateCourseStatus(course);

        //在sql语句需要多个参数时，一般都是用类来当参数，而不是分开的参数，否则当需要添加参数时
        //对应的地方都要添加，而使用类，只需要在这里调用一下set方法就可以了
    }
}
