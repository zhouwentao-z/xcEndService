package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 周文韬
 * @create 2020-12-16 11:53:32
 * @desc ...
 */
public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    //根据课程id和parentId，select * from teachplan a where a.courseid ='4028e581617f945f01617f9dabc40000' and a.parentid='0'
    public List<Teachplan> findByCourseidAndParentid(String courseId,String parentId);
}
