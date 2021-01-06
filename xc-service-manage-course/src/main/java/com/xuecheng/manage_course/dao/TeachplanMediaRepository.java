package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 周文韬
 * @create 2020-12-16 11:53:32
 * @desc ...
 */
public interface TeachplanMediaRepository extends JpaRepository<TeachplanMedia,String> {
    //根据课程Id查询
    List<TeachplanMedia> findByCourseId(String cousreId);
}
