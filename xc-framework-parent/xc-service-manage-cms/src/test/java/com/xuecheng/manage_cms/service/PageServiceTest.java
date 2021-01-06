package com.xuecheng.manage_cms.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {

    @Autowired
    PageService pageService;
    @Test
    public void getPageHtml() {
        String ht = pageService.getPageHtml("5fd873f252eea5c1e88a5765");
        System.out.println(ht);
    }
}
