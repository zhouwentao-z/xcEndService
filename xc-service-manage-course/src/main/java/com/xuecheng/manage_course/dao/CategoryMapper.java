package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 周文韬
 * @create 2020-12-16 18:27:19
 * @desc ...
 */
@Mapper
public interface CategoryMapper {
    //查询分类
    public CategoryNode selectList();
}
