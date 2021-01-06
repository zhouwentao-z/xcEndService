package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 周文韬
 * @create 2020-12-16 14:50:28
 * @desc ...
 */
@Api(value = "课程分类管理",produces = "课程分类管理")
public interface CategoryControllerApi {
    @ApiOperation("分类查询")
    public CategoryNode findList();

}
