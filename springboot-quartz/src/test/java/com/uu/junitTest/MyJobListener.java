package com.uu.junitTest;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

/**
 * 描述：
 *
 * @author liupenghao
 **/
public class MyJobListener extends JobListenerSupport {
    /**
     * <p>
     * Get the name of the <code>JobListener</code>.
     * </p>
     */
    public String getName() {
        return "myJobLister";
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        super.jobExecutionVetoed(context);
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("----------- job 将要被执行--------");
        super.jobToBeExecuted(context);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("----------- job 被执行完毕--------");
        super.jobWasExecuted(context, jobException);
    }
}
