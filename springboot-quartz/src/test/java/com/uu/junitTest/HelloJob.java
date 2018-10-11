package com.uu.junitTest;

import lombok.Data;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述：
 *
 * @author liupenghao
 **/
@Data
public class HelloJob implements Job {


    /**
     * .usingJobData("name", "张三")
     * .usingJobData("characteristic", "帅")
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        String name = jobDataMap.get("name").toString();
        String characteristic = jobDataMap.get("characteristic").toString();

        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        String mergedName = mergedJobDataMap.getString("name");

        System.out.println("name : " + name);
        System.out.println("mergedName:" + mergedName);

        System.out.println("characteristic : " + characteristic);

    }
}
