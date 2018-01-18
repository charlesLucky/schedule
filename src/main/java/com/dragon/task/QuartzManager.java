package com.dragon.task;


import com.platform.beans.Schedule;
import com.platform.dao.ScheduleDao;
import com.platform.helper.ScheduleHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("quartzManager")
@Scope(value = "singleton")
public class QuartzManager {

    /* 当初我初始化的是  SchedulerFactoryBean schedulerFactoryBean；  这样是注入不进去的 报下面的错 
       nested exception is org.springframework.beans.factory.BeanNotOfRequiredTypeException: 
       Bean named 'schedulerFactoryBean' must be of  
       type [org.springframework.scheduling.quartz.SchedulerFactoryBean],  
       but was actually of type [org.quartz.impl.StdScheduler>] 
       看spring源码可以知道，其实spring得到的是一个工厂bean，得到的不是它本身，而是它负责创建的org.quartz.impl.StdScheduler对象            所以要使用Scheduler对象 
     */
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleDao taskService;
    private Logger log = Logger.getLogger(QuartzManager.class);

    @PostConstruct
    private void init() {
        try {

            List<Schedule> jobLists = taskService.queryAll(new Schedule());
            for (Schedule scheduleJob : jobLists) {
                if (JobStatus.ENABLE.equals(scheduleJob.getStatus())) {
                    addJob(scheduleJob);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("任务调度初始化失败！！");
        }

    }

    /**
     * @param scheduleJob
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     */
    public void addJob(Schedule scheduleJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getName(), scheduleJob.getGroupName());
            //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //不存在，创建一个
            if (null == trigger) {
                Class jobclass = null;
                jobclass = QuartzJob.class;
                JobDetail jobDetail = JobBuilder.newJob(jobclass)
                        .withIdentity(scheduleJob.getName(), scheduleJob.getGroupName()).build();
                jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob
                        .getCronExpression());
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getName(), scheduleJob.getGroupName()).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在，那么更新相应的定时设置
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob
                        .getCronExpression());
                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder).build();
                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    public List<Schedule> getPlanJobs() {
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            List<Schedule> jobList = new ArrayList<Schedule>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Schedule job = new Schedule();
                    job.setName(jobKey.getName());
                    job.setGroupName(jobKey.getGroup());
                    job.setDescription("触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCronExpression(cronExpression);
                    }
                    jobList.add(job);
                }
            }
            return jobList;
        } catch (SchedulerException ex) {
            java.util.logging.Logger.getLogger(QuartzManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Schedule> getRunningJbos() {
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            List<Schedule> jobList = new ArrayList<Schedule>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Schedule job = new Schedule();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setName(jobKey.getName());
                job.setGroupName(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
            return jobList;
        } catch (SchedulerException ex) {
            java.util.logging.Logger.getLogger(QuartzManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void pauseJob(Schedule scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroupName());
        scheduler.pauseJob(jobKey);
    }

    public void resumeJob(Schedule scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroupName());
        scheduler.resumeJob(jobKey);
    }

    public void removeJob(Schedule scheduleJob) {
        System.out.println("*****************************************");
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroupName());
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException ex) {
            java.util.logging.Logger.getLogger(QuartzManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void executeJob(Schedule scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroupName());
        scheduler.triggerJob(jobKey);
    }

    public void updateJob(Schedule scheduleJob) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getName(),
                scheduleJob.getGroupName());
        //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob
                .getCronExpression());
        //按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(scheduleBuilder).build();
        //按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * @Description:启动所有定时任务
     *
     *
     * @Title: QuartzManager.java
     * @Copyright: Copyright (c) 2014
     *
     * @author Comsys-LZP
     * @date 2014-6-26 下午03:50:18
     * @version V2.0
     */
    public void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     *
     *
     * @Title: QuartzManager.java
     * @Copyright: Copyright (c) 2014
     *
     * @author Comsys-LZP
     * @date 2014-6-26 下午03:50:26
     * @version V2.0
     */
    public void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
