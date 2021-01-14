package com.xuecheng.order.mq;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.config.RabbitMQConfig;
import com.xuecheng.order.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author 周文韬
 * @create 2021-01-13 15:57:03
 * @desc ...
 */
@Component
public class ChooseCourseTask {
    private  static  final  Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);

    @Autowired
    TaskService taskService;
    /**
     * 接收选课响应结果
     */
    @RabbitListener(queues = RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE)
    public void receiveFinishChoosecourseTask(XcTask xctask){
        if (xctask!=null && StringUtils.isNotBlank(xctask.getId())){
            //删除任务
            taskService.finishTask(xctask.getId());
        }

    }

    @Scheduled(cron = "0/3 * * * * *")
    //定时添加选课任务
    public void sendChoosecourseTask(){
        //得到一分钟之前的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MINUTE,-1);
        Date time = calendar.getTime();
        List<XcTask> xcTaskList = taskService.findXcTaskList(time, 100);
        System.out.println(xcTaskList);
        //调用service发布消息，将添加选课的任务发送给mq
        for (XcTask xcTask:xcTaskList){
            if (taskService.getTask(xcTask.getId(),xcTask.getVersion())>0){
                String mqExchange = xcTask.getMqExchange();     //交换机
                String routingkey = xcTask.getMqRoutingkey();   //routingkey
                taskService.publish(xcTask,mqExchange,routingkey);
            }
        }
    }

    //定义任务调度策略
//    @Scheduled(cron = "0/3 * * * * *")//每隔3秒去执行
//    @Scheduled(fixedRate = 3000)     //任务开始后3秒执行下一次调度
//    @Scheduled(fixedDelay = 3000)   //任务结束后3秒执行下一次调度
//    @Scheduled(initialDelay = 3000,fixedRate = 5000) //第一次延迟3秒，以后每隔5秒执行一次
    public void task01(){
        LOGGER.info("+++++++++++测试任务1开始+++++++++++");
    try {
        Thread.sleep(5000);
    }catch (InterruptedException e){
        e.printStackTrace();
    }
    LOGGER.info("================测试任务1结束=====================");
    }
    //定义任务调度策略
//    @Scheduled(cron = "0/3 * * * * *")//每隔3秒去执行
//    @Scheduled(fixedRate = 3000)     //任务开始后3秒执行下一次调度
//    @Scheduled(fixedDelay = 3000)   //任务结束后3秒执行下一次调度
    public void task02(){
        LOGGER.info("+++++++++++测试任务2开始+++++++++++");
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        LOGGER.info("================测试任务2结束=====================");
    }
}
