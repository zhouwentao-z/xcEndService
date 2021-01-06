package com.xuecheng.manage_cms;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author 周文韬
 * @create 2020-12-21 22:33:02
 * @desc ...
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {
    @Autowired
    GridFsTemplate gridFsTemplate;

    @Test
    public  void testStore(){
        try {
            File file = new File("f:/course.ftl");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectId store = gridFsTemplate.store(fileInputStream, "course.ftl");
            System.out.println(store);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
