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

        try {
            // 执行任务
            System.out.println("-------执行吃饭任务----------");

            logger.info("吃饭中。。。。。。。");
            Thread.sleep(2000);

            logger.info("吃完饭了。。。。。。");

        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：");
        }
    }
}
