package com.myproj.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author shenxie
 * @date 2021/1/3
 */
public class JobDemo implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("===========JobDemo:"+ new Date() +"============");
    }
}
