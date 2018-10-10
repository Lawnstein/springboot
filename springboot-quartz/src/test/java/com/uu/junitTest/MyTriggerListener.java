package com.uu.junitTest;

import org.quartz.listeners.TriggerListenerSupport;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-09-27 下午4:04
 **/
public class MyTriggerListener extends TriggerListenerSupport {
    /**
     * <p>
     * Get the name of the <code>TriggerListener</code>.
     * </p>
     */
    public String getName() {
        return "myTriggerLister";
    }


}
