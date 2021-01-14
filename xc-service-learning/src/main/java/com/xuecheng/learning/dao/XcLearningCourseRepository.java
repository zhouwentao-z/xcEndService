package com.xuecheng.learning.dao;

import com.xuecheng.framework.domain.learning.XcLearningCourse;
import com.xuecheng.framework.domain.learning.XcLearningList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 周文韬
 * @create 2021-01-13 21:40:07
 * @desc ...
 */
public interface XcLearningCourseRepository extends JpaRepository<XcLearningCourse,String> {

    //根据用户的id和课程Id查询
    XcLearningCourse findByUserIdAndCourseId(String userId,String courseId);
}
