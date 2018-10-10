package com.uu.job;


import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务
 *
 * @author MrBird
 */
public class ScheduleJob extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) {
        long startTime = System.currentTimeMillis();

        try {
            // 执行任务
            System.out.println("-------任务开始执行吃饭任务----------");
            Thread.sleep(2000);

            logger.info("吃饭中。。。。。。。");

            long times = System.currentTimeMillis() - startTime;

            logger.info("吃完饭了。。。。。。");

        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：");
            long times = System.currentTimeMillis() - startTime;
            // 任务状态 0：成功 1：失败
        } finally {

        }
    }
}
