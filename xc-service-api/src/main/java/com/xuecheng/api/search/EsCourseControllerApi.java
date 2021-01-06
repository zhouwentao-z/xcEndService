package com.xuecheng.api.search;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * @author 周文韬
 * @create 2020-12-24 19:57:55
 * @desc ...
 */
@Api(value = "课程搜索",description = "课程搜索",tags = "{课程搜索}")
public interface EsCourseControllerApi {
    @ApiOperation("课程搜索")
    public  QueryResponseResult<CoursePub> list (int page, int size, CourseSearchParam courseSearchParam) throws Exception;

    @ApiOperation("根据课程Id查询课程计划")
    public Map<String, CoursePub> getall(String id);

    @ApiOperation("根据课程计划查询媒资信息")
    public TeachplanMediaPub getmedia(String teachplanId);
}
