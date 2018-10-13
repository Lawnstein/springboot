package com.uu.husky.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-19 16:41
 **/
@Component
public class SchedulerTask {
    private int count = 0;

    @Scheduled(cron="0/6 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }
}
