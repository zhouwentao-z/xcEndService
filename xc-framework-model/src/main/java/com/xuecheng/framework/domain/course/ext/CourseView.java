package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 周文韬
 * @create 2020-12-21 17:44:58
 * @desc ...
 */
@Data
@ToString
@NoArgsConstructor
public class CourseView  implements Serializable {

    //基础信息
    private  CourseBase courseBase;
    //课程图片
    private  CoursePic coursePic;
    //课程营销
    private CourseMarket courseMarket;
    //教学计划
    private  TeachplanNode teachplanNode;
}
