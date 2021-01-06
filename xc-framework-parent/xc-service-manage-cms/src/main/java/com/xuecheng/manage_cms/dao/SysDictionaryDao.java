package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 周文韬
 * @create 2020-12-18 17:57:21
 * @desc ...
 */
@Repository
public interface SysDictionaryDao extends MongoRepository <SysDictionary,String>{
    //根据字典分类查询信息
    SysDictionary findBydType(String type);
}
