package com.xuecheng.manage_media_process;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-07-12 9:11
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestProcessBuilder {
    @Test
    public void testProcessBuilder()throws Exception{
        ProcessBuilder processBuilder = new ProcessBuilder();
        //processBuilder.command("ping","www.baidu.com");
        processBuilder.command("ipconfig");
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        //启动进程
        Process start = processBuilder.start();
        //获取输入流
        InputStream inputStream = start.getInputStream();
        //转成字符输入流
        InputStreamReader reader = new InputStreamReader(inputStream,"gbk");
        int len = -1;
        char[] c = new char[1024];
        StringBuffer buffer = new StringBuffer();
        while ((len=reader.read(c))!=-1){
            String s = new String(c,0,len);
            buffer.append(s);
            System.out.println(s);
        }
        inputStream.close();
        reader.close();
    }

}
