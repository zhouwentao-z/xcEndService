package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 周文韬
 * @create 2020-12-18 10:33:53
 * @desc ...
 */
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {
}
