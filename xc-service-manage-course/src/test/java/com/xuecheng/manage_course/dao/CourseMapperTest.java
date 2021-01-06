package com.xuecheng.manage_course.dao;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;


/**
 * @author 周文韬
 * @create 2020-12-16 16:15:30
 * @desc ...
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseMapperTest {
   @Autowired
   CourseMapper courseMapper;

   @Test
    public void testPageHepeler(){
        PageHelper.startPage(2,1);
        CourseListRequest courseListRequest = new CourseListRequest();
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        List<CourseInfo> reslut = courseListPage.getResult();
        System.out.println(courseListPage);
    }
}
