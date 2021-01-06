package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.manage_cms.service.PageService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ResTemplatetest {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Test
    public void testGirdFsTemplate() throws Exception{
        //要存储的文件
        File file = new File("f:course.ftl");
        //定义输入流
        FileInputStream inputStram = new FileInputStream(file);
        //向GridFS存储文件
        ObjectId objectId = gridFsTemplate.store(inputStram, "轮播图测试文件02");
        //得到文件ID
        String fileId = objectId.toString();
        System.out.println(file);

    }
    @Test
    public void queryFile() throws IOException{
        GridFSFile gridFSFile= gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5fd86d3a52eea58c907703c3")));

        //打开一个下载流对象
        GridFSDownloadStream gridFSDownloadStream= gridFSBucket.openDownloadStream(gridFSFile.getId());

        //创建GridFSResource对象 获取流
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //从流中来取数据
        String s = IOUtils.toString(gridFsResource.getInputStream(),"utf-8");
        System.out.println(s);
    }
}
