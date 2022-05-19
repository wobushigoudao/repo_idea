package com.lagou.dao;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;

import java.util.List;

/**
 *
 */
public interface CourseContentMapper {

    /*
    根据课程id查询课程内容（后面通过这个来回显），章节信息以及其对应的课时信息
     */
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    /*
    回显章节对应的课程信息
     */
    public Course findCourseByCourseId(Integer courseId);

    /*
    新建章节信息
     */
    public void saveSection(CourseSection courseSection);
    /*
       更新章节信息
     */
    public void updateSection(CourseSection courseSection);

    /*
    修改章节状态
     */
    public void updateSectionStatus(CourseSection courseSection);

    /*
    添加课时信息
     */
    public void saveLesson(CourseLesson courseLesson);

    /*
    修改课时信息
     */
    public void updateLesson(CourseLesson courseLesson);

    /*
   修改课时状态
    */
    public void updateLessonStatus(CourseLesson courseLesson);
}
