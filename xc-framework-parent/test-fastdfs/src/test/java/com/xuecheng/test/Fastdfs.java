package com.xuecheng.test;

import com.xuecheng.test.fastdfs.TestFastDFSApplication;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;


/**
 * @author 周文韬
 * @create 2020-12-17 14:57:51
 * @desc ...
 */
@SpringBootTest(classes = TestFastDFSApplication.class)
public class Fastdfs {

    //上传测试
    @Test
    public void testUpload() {
        try {
            //加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义TrackerClient
            TrackerClient trackerClient = new TrackerClient();
            //连接Tracker服务器
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Storage
            StorageServer storage = trackerClient.getStoreStorage(trackerServer);
            //创建storageClient
            StorageClient1 storageClient1= new StorageClient1(trackerServer,storage);
            //本地文件路径
            String filePath ="F:/WinSCP/PuTTY/123456.mp4";
            String fileId = storageClient1.upload_file1(filePath, "mp4", null);
            System.out.println(fileId);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testDownload(){
        try {
            //加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义TrackerClient
            TrackerClient trackerClient = new TrackerClient();
            //连接Tracker服务器
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Storage
            StorageServer storage = trackerClient.getStoreStorage(trackerServer);
            //创建storageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storage);
            //本地文件路径
            String fileId ="group1/M00/00/00/wKi9g1_bCZKAH44yESJ2a8760Jg827.mp4";
            byte[] bytes = storageClient1.download_file1(fileId);
            FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/1234.mp4"));
            fileOutputStream.write(bytes);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
