package com.uu.junitTest;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-09-26 下午10:40
 **/

public class QuartzTest {

    public static void main(String[] args) throws SchedulerException, ParseException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        // 定义job,绑定我们的定时任务
        JobDetail job2 = newJob(HelloJob.class)
                .usingJobData("name", "张三")
                .usingJobData("characteristic", "帅3")
                .build();

        // 执行任务，用定义好的触发器 和 任务
        scheduler.scheduleJob(job2, getTrigger6());

    }

    /**
     * 触发器立即触发，然后每隔2秒 触发一次，22:55:00：
     */
    private static Trigger getTrigger1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        //定义一个任务触发器
        return newTrigger()
                .withIdentity("job2", "group2")
                //定点触发
                //.startAt(sdf.parse("2018-09-27 10:27:00"))
                // 五秒钟后触发
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                //.withRepeatCount(0))
                .endAt(DateBuilder.dateOf(22, 55, 0))
                .build();
    }

    /**
     * 触发器 2018-09-27 10:27:00 定点触发，重复0次
     */
    private static Trigger getTrigger2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        //定义一个任务触发器
        return newTrigger()
                .withIdentity("job1", "group1")
                //定点触发
                .startAt(sdf.parse("2018-09-27 10:27:00"))
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever()
                        .withRepeatCount(0))
                .build();

    }

    /**
     * 触发器 在下一个整点小时触发，然后每隔2小时触发一次，永不停歇：次
     */
    private static Trigger getTrigger3() throws ParseException {
        //定义一个任务触发器
        return newTrigger()
                .withIdentity("job1", "group1")
                //定点触发
                .startAt(DateBuilder.evenHourDate(null))
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever()
                        .withRepeatCount(0))
                .build();
    }

    /**
     * 触发器，每天从下午15点到下午16点，每隔2秒钟触发一次：
     */
    private static Trigger getTrigger4() throws ParseException {
        //定义一个任务触发器
        return newTrigger()
                .withIdentity("job1", "group1")
                //定点触发
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 0 15-16 * * ?"))
                .forJob("job1", "group1")
                .build();
    }

    /**
     * 触发器，每天从的下午3:33 触发一次
     */
    private static Trigger getTrigger5() throws ParseException {
        //定义一个任务触发器
        return newTrigger()
                .withIdentity("job1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 27 16  * * ?"))
                //或者下边这样写
                // .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(15, 33))
                .forJob("job1", "group1")
                .build();
    }

    /**
     * 触发器，每天从的下午3:33 触发一次
     */
    private static Trigger getTrigger6() throws ParseException {
        //定义一个任务触发器
        return newTrigger()
                .withIdentity("job77", "group77")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * *  * * ?"))
                //或者下边这样写
                // .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(15, 33))
                .build();
    }

    /**
     * test 类无法正常运行
     *
     * @throws SchedulerException
     */
    @Test
    public void simpleTest() throws SchedulerException {

    }

}
