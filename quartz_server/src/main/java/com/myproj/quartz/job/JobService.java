package com.myproj.quartz.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author shenxie
 * @date 2021/1/3
 */
@Service
public class JobService {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.start();
        JobDetail detail  = JobBuilder.newJob(JobDemo.class).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?").inTimeZone(TimeZone.getTimeZone("Asia/Shanghai")))
                .withIdentity("name9" + (int)(Math.random()*1000),"group3")
                .startNow()
                .build();
        scheduler.scheduleJob(detail,trigger);
    }
}
