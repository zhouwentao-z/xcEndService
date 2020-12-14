package com.xuecheng.manage_cms_client.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 监听MQ，接收页面发布消息
 * @author 周文韬
 * @create 2020-12-04 22:08:18
 * @desc ...
 */
@Configuration
public class MongConfig {
    @Value("${spring.data.mongodb.database}")
    String db;

    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient){
       MongoDatabase database= mongoClient.getDatabase(db);
       GridFSBucket bucket = GridFSBuckets.create(database);
       return bucket;
    }
}
