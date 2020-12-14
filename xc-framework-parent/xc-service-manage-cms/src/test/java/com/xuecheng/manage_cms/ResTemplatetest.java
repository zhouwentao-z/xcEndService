package com.xuecheng.manage_cms;

import com.xuecheng.manage_cms.service.PageService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ResTemplatetest {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Test
    public void testGirdFsTemplate() throws Exception{
        //要存储的文件
        File file = new File("d:/index_banner.ftl");
        //定义输入流
        FileInputStream inputStram = new FileInputStream(file);
        //向GridFS存储文件
        ObjectId objectId = gridFsTemplate.store(inputStram, "轮播图测试文件02");
        //得到文件ID
        String fileId = objectId.toString();
        System.out.println(file);

    }
}
