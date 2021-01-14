package com.xuecheng.learning.service;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.learning.XcLearningCourse;
import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import com.xuecheng.framework.domain.learning.response.LearningCode;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.learning.client.CourseSearchClient;
import com.xuecheng.learning.dao.XcLearningCourseRepository;
import com.xuecheng.learning.dao.XcTaskHisRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * @author 周文韬
 * @create 2021-01-06 18:46:54
 * @desc ...
 */
@Service
public class LearningService {
    @Autowired
    CourseSearchClient courseSearchClient;
    @Autowired
    XcLearningCourseRepository xcLearningCourseRepository;
    @Autowired
    XcTaskHisRepository xcTaskHisRepository;

    public GetMediaResult getmedia(String courseId, String teachplanId) {
        //校验学生的学习权限

        //远程调用搜索服务查询课程计划所对应的课程媒资信息
        TeachplanMediaPub teachplanMediaPub = courseSearchClient.getmadie(teachplanId);
        if (teachplanMediaPub == null || StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())){
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        return  new GetMediaResult(CommonCode.SUCCESS,teachplanMediaPub.getMediaUrl());
    }

    /**
     * 添加选课
     * @param userId
     * @param courseId
     * @param valid
     * @param startTime
     * @param endTime
     * @return
     */
    @Transactional
    public ResponseResult addcourse(String userId, String courseId, String valid, Date startTime, Date endTime, XcTask xcTask){
        if (StringUtils.isBlank(courseId)) {
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        if (StringUtils.isBlank(userId)){
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_USERISNULL);
        }
        if (xcTask == null || StringUtils.isEmpty(xcTask.getId())){
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_TASKISNULL);
        }
        XcLearningCourse xcLearningCourse = xcLearningCourseRepository.findByUserIdAndCourseId(userId, courseId);
            if (xcLearningCourse == null){
                xcLearningCourse=new XcLearningCourse();
                //课程开始时间
                xcLearningCourse.setUserId(userId);
                xcLearningCourse.setCourseId(courseId);
                xcLearningCourse.setValid(valid);
                xcLearningCourse.setStartTime(startTime);
                xcLearningCourse.setEndTime(endTime);
                xcLearningCourse.setStatus("501001");
                xcLearningCourseRepository.save(xcLearningCourse);
            }else {
                xcLearningCourse.setStartTime(startTime);
                xcLearningCourse.setEndTime(endTime);
                xcLearningCourse.setValid(valid);
                xcLearningCourse.setStatus("501001");
                xcLearningCourseRepository.save(xcLearningCourse);
            }
        Optional<XcTaskHis> optional = xcTaskHisRepository.findById(xcTask.getId());
           if (!optional.isPresent()){
               //添加历史任务
               XcTaskHis xcTaskHis = new XcTaskHis();
               BeanUtils.copyProperties(xcTask,xcTaskHis);
               xcTaskHisRepository.save(xcTaskHis);
           }
           return  new ResponseResult(CommonCode.SUCCESS);
    }
}
