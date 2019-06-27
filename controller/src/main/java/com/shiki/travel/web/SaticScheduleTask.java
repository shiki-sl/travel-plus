package com.shiki.travel.web;

import com.shiki.travel.web.service.impl.RouteImgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SaticScheduleTask {

    @Autowired
    private RouteImgServiceImpl routeImgService;

    //3.添加测试定时任务
    @Scheduled(cron = "0 0/1 * * * ?")
//    或直接指定时间间隔，例如：5秒
//    @Scheduled(fixedRate=5000)
    private void configureTasksTest() {
        System.out.println("执行测试定时任务时间Test: " + LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 1 * * ?")
    private void updateSolr() {
        routeImgService.clean();
    }
}