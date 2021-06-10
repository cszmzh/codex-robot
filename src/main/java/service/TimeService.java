package service;

import job.RobotJob;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.*;

public class TimeService {
    /**
     * 每日提醒
     */
    public void Mission() throws SchedulerException {

        // 创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 创建一个触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(cronSchedule("0 0 0,7 * * ?"))
                .build();

        // 创建一个任务
        JobDetail job = JobBuilder.newJob(RobotJob.class).build();

        // 注册trigger并启动
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}
