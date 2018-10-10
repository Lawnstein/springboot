package com.uu.web;

import com.uu.job.ScheduleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 描述：
 *
 * @author liupenghao
 **/
@RequestMapping(value = "/job")
@Controller
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping
    @ResponseBody
    public Object test() throws SchedulerException {
        scheduler.start();
        // 定义job,绑定我们的定时任务
        JobDetail job2 = newJob(ScheduleJob.class).build();

        // 执行任务，用定义好的触发器 和 任务
        scheduler.scheduleJob(job2, getTrigger1());
        return "{status:OK}";
    }

    /**
     * 触发器立即触发，然后每隔2秒 触发一次，22:55:00：
     */
    private static Trigger getTrigger1() {
        //定义一个任务触发器
        return newTrigger()
                .withIdentity("job15", "group15")
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
     * 添加定时任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @return
     */
    @PostMapping("/addJob")
    @ResponseBody
    public Map<String, String> addJob(@RequestParam(value = "jobClassName") String jobClassName,
                                      @RequestParam(value = "jobGroupName") String jobGroupName,
                                      @RequestParam(value = "cronExpression") String cronExpression) {
        Map<String, String> returnData = new HashMap<>();
        try {

            JobDetail jobDetail = JobBuilder
                    .newJob(getClass(jobClassName).getClass())
                    .withIdentity("测试-1-jojobdetailbdetail")
                    .build();

            //构建CronTrigger触发器
            CronTrigger cronTrigger = TriggerBuilder
                    .newTrigger()
                    .withSchedule(cronSchedule(cronExpression)
                            .withMisfireHandlingInstructionDoNothing()
                    )
                    .withIdentity("测试-1-cronTrigger")
                    .build();

            //注册调度任务
            scheduler.scheduleJob(jobDetail, cronTrigger);
            //启动任务
            scheduler.start();

            returnData.put("msg", "添加调度任务成功");
        } catch (Exception e) {
            returnData.put("msg", "添加调度任务异常：" + e.getMessage());
        }
        return returnData;
    }


    /**
     * 暂停定时任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @return
     */
    @PutMapping(value = "/pauseJob")
    @ResponseBody
    public Map<String, String> pauseJob(@RequestParam(value = "jobClassName") String jobClassName,
                                        @RequestParam(value = "jobGroupName") String jobGroupName) {
        Map<String, String> returnData = new HashMap<String, String>();
        try {
            //JobKey定义了job的名称和组别
            JobKey jobKey = JobKey.jobKey(jobClassName, jobGroupName);
            //暂停任务
            scheduler.pauseJob(jobKey);

            returnData.put("msg", "暂停调度任务成功");
        } catch (SchedulerException e) {
            returnData.put("msg", "暂停调度任务异常：" + e.getMessage());
        } catch (Exception e) {
            returnData.put("msg", "暂停调度任务异常：" + e.getMessage());
        }

        return returnData;
    }

    /**
     * 启动已经暂停的任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @return
     */
    @PutMapping(value = "/resumeJob")
    @ResponseBody
    public Map<String, String> resumeJob(String jobClassName,
                                         String jobGroupName) {
        Map<String, String> returnData = new HashMap<String, String>();
        try {
            //JobKey定义了job的名称和组别
            JobKey jobKey = JobKey.jobKey(jobClassName, jobGroupName);
            //继续任务
            scheduler.resumeJob(jobKey);
            returnData.put("msg", "继续调度任务成功");
        } catch (SchedulerException e) {
            returnData.put("msg", "继续调度任务异常：" + e.getMessage());
        } catch (Exception e) {
            returnData.put("msg", "继续调度任务异常：" + e.getMessage());
        }

        return returnData;
    }

    /**
     * 更新定时任务：
     * --传入的triggerKey有与之匹配的
     * --旧触发器的触发时间没有完成
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @return
     */
    @PutMapping(value = "/rescheduleJob")
    @ResponseBody
    public Map<String, String> rescheduleJob(String jobClassName,
                                             String jobGroupName,
                                             String cronExpression) {
        Map<String, String> returnData = new HashMap<String, String>();
        try {

            //构建旧的TriggerKey
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);

            //通过cron表达式构建CronScheduleBuilder
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            //从调度容器中获取旧的CronTrigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            //更新CronTrigger
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    //工作项1：job名以及所属组
                    .withSchedule(scheduleBuilder)
                    //工作项2：指定调度参数
                    .build();//构建

            //更新调度任务
            scheduler.rescheduleJob(triggerKey, trigger);

            returnData.put("msg", "更新调度任务成功");
        } catch (Exception e) {
            returnData.put("msg", "更新调度任务异常：" + e.getMessage());
        }

        return returnData;
    }


    /**
     * @param jobClassName
     * @param jobGroupName
     * @return
     */
    @DeleteMapping(value = "/removeJob")
    @ResponseBody
    public Map<String, String> removeJob(String jobClassName,
                                         String jobGroupName) {
        Map<String, String> returnData = new HashMap<String, String>();
        try {
            //获得调度容器
            //Scheduler scheduler = getCurrentScheduler();
            //TriggerKey定义了trigger的名称和组别
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);

            //暂停触发器
            scheduler.resumeTrigger(triggerKey);
            //暂停触发器
            scheduler.unscheduleJob(triggerKey);
            //移除任务
            scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));

            returnData.put("msg", "删除调度任务成功");
        } catch (SchedulerException e) {
            returnData.put("msg", "删除调度任务异常：" + e.getMessage());
        } catch (Exception e) {
            returnData.put("msg", "删除调度任务异常：" + e.getMessage());
        }

        return returnData;
    }


    /**
     * 获得指定的类实例
     *
     * @param classname
     * @return
     * @throws ServerException
     */
    private Job getClass(String classname) throws ServerException {
        Job baseJob = null;
        try {
            //加载参数指定的类
            Class<?> classTmp = Class.forName(classname);
            //实例化
            baseJob = (Job) classTmp.newInstance();
        } catch (Exception e) {
            System.out.println(classname + "......找不到相应的类");
        }

        return baseJob;
    }
}
