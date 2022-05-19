package com.lagou.service.impl;

import com.lagou.dao.CourseContentMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class CourseContentServiceImpl implements CourseContentService {

    @Autowired
    private CourseContentMapper courseContentMapper;

    //根据课程id查询课程内容（后面通过这个来回显），章节信息以及其对应的课时信息
    @Override
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId) {
        List<CourseSection> sectionAndLessonByCourseId = courseContentMapper.findSectionAndLessonByCourseId(courseId);
        return sectionAndLessonByCourseId;
    }

    /*
 回显章节对应的课程信息
  */
    @Override
    public Course findCourseByCourseId(Integer courseId) {
        Course courseByCourseId = courseContentMapper.findCourseByCourseId(courseId);
        return courseByCourseId;
    }

    /*
    新建章节信息
     */
    @Override
    public void saveSection(CourseSection courseSection) {
        Date date = new Date();
        courseSection.setCreateTime(date);
        courseSection.setUpdateTime(date);
        courseContentMapper.saveSection(courseSection);
    }

    /*
    更新章节信息
     */
    @Override
    public void updateSection(CourseSection courseSection) {
        courseSection.setUpdateTime(new Date());
        courseContentMapper.updateSection(courseSection);
    }

    /*
   修改章节状态
    */
    @Override
    //很少的参数，一般就不需要创建对象了，节省一点空间
    public void updateSectionStatus(int id,int status) {

        //封装数据
        CourseSection courseSection = new CourseSection();
        courseSection.setId(id);
        courseSection.setStatus(status);
        courseSection.setUpdateTime(new Date());

        //调用mapper
        courseContentMapper.updateSectionStatus(courseSection);
    }

    /*
   添加课时信息
    */
    @Override
    public void saveLesson(CourseLesson courseLesson) {
        Date date = new Date();
        courseLesson.setCreateTime(date);
        courseLesson.setUpdateTime(date);
        courseContentMapper.saveLesson(courseLesson);
    }

    /*
   修改课时信息
    */
    @Override
    public void updateLesson(CourseLesson courseLesson) {
        courseLesson.setUpdateTime(new Date());
        courseContentMapper.updateLesson(courseLesson);
    }

    /*
  修改课时状态
   */
    @Override
    public void updateLessonStatus(CourseLesson courseLesson) {
        courseLesson.setUpdateTime(new Date());
        courseContentMapper.updateLessonStatus(courseLesson);
    }

}
