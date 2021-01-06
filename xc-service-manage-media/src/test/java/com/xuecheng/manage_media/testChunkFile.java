package com.xuecheng.manage_media;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * @author 周文韬
 * @create 2021-01-04 14:49:55
 * @desc ...
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class testChunkFile {

    /**
     * 测试文件分块
     * @throws IOException
     */
    @Test
    public void testchunks() throws IOException {
        //源文件
        File sourceFile = new File("D:\\test\\ffmpeg\\lucene.avi");
        //块目录
        String chunkFileFolder = "D:\\test\\ffmpeg\\chunks\\";
        //定义块大小
        long chunksFileSize =1*1024*1024;

        //块数
        long chunksFileNum = (long)Math.ceil(sourceFile.length()*1.0/chunksFileSize);
        //创建读文件的缓冲区
        RandomAccessFile raf_read =  new RandomAccessFile(sourceFile,"r");
        //缓冲区
        byte[] b = new byte[1024];
        for(int i=0;i<chunksFileNum;i++){
            //块文件
            File chunkFile = new File(chunkFileFolder+i);
            //创建向块文件写对象
            RandomAccessFile raf_write = new RandomAccessFile(chunkFile, "rw");
            int len = -1;
            while ((len=raf_read.read(b))!=-1){
                raf_write.write(b,0,len);
                //如果块文件达到1M，开始写下一块儿
                if (chunkFile.length()>=chunksFileSize) {
                    break;
                }
            }
        raf_write.close();
        }
        raf_read.close();
    }

    @Test
    public void test() throws IOException {
        //块文件目录
        String chunkFileFolderPath = "D:\\test\\ffmpeg\\chunks\\";
        //拿到文件目录对象
        File chunkFileFolder = new File(chunkFileFolderPath);
        //将文件列表
        File[] files = chunkFileFolder.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (Integer.parseInt(o1.getName())>Integer.parseInt(o2.getName())){
                    return  1;
                }
                return -1;
            }
        });
        //合并文件
        File mergeFile = new File("D:\\test\\ffmpeg\\lucene1.avi");

        boolean newFil = mergeFile.createNewFile();

        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
        byte[] b = new byte[1024];
        for (File chunkFile :fileList){
            //创建一个读块文件对象
            RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "r");
            int len = -1;
            while ((len=raf_read.read(b))!=-1){
                raf_write.write(b,0,len);
            }
            raf_read.close();
        }
        raf_write.close();
    }
}
