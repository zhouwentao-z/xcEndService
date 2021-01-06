package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 周文韬
 * @create 2020-12-18 17:35:05
 * @desc ...
 */
@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 查询分类
     * @return
     */
    public CategoryNode findList(){
        return  categoryMapper.selectList();
    }
}
