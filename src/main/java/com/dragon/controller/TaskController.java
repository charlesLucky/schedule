/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dragon.controller;

import com.alibaba.fastjson.JSON;
import com.dragon.task.QuartzManager;
import com.dragon.task.DataGrid;
import com.dragon.task.JobStatus;
import com.platform.beans.Schedule;
import com.platform.dao.ScheduleDao;
import io.swagger.annotations.Api;
import java.util.List;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ban
 */
@Api(value = "TaskController", description = "銀行")
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private ScheduleDao taskService;

    @RequestMapping(value = "/planTasks", method = RequestMethod.GET)
    public Object getPlanTasks() throws SchedulerException {
        List<Schedule> planJobs = quartzManager.getPlanJobs();
        return JSON.toJSON(planJobs);
    }

    @RequestMapping(value = "/planTasks2", method = RequestMethod.POST)
    public Object getPlanTasks2() throws SchedulerException {
        List<Schedule> planJobs = quartzManager.getPlanJobs();
        return JSON.toJSON(planJobs);
    }

    @RequestMapping(value = "/runningTasks", method = RequestMethod.GET)

    public Object getRunningTasks() throws SchedulerException {
        List<Schedule> runningJbos = quartzManager.getRunningJbos();
        return JSON.toJSON(runningJbos);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public Object getAllTasks() throws SchedulerException {
        List<Schedule> list = taskService.queryAll(new Schedule());
        DataGrid dg = new DataGrid();
        dg.setTotal(list.size());
        dg.setRows(list);
        return JSON.toJSON(dg);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public Object getAllTasksget() throws SchedulerException {
        List<Schedule> list = taskService.queryAll(new Schedule());
        DataGrid dg = new DataGrid();
        dg.setTotal(list.size());
        dg.setRows(list);
        return JSON.toJSON(dg);
    }

    @RequestMapping(value = "/addJobs", method = RequestMethod.POST)
    public String addJobs(@RequestBody Schedule scheduleJob) {
        taskService.add(scheduleJob);
        if (JobStatus.ENABLE.equals(scheduleJob.getStatus())) {
            quartzManager.addJob(scheduleJob);
        }
        return "true";
    }

    @RequestMapping(value = "/addJobs2", method = RequestMethod.POST)
    public String addJobs2(Schedule scheduleJob) {
        taskService.add(scheduleJob);
        if (JobStatus.ENABLE.equals(scheduleJob.getStatus())) {
            quartzManager.addJob(scheduleJob);
        }
        return "true";
    }

    @RequestMapping(value = "/updateJob", method = RequestMethod.POST)
    public String updateJob(@RequestBody Schedule scheduleJob) throws SchedulerException {
        taskService.update(scheduleJob);
        quartzManager.removeJob(scheduleJob);
        if (JobStatus.ENABLE.equals(scheduleJob.getStatus())) {
            quartzManager.addJob(scheduleJob);
        }
//        Schedule oldJob = taskWS.getById(scheduleJob.getId());
//        if (oldJob.getStatus().equals(scheduleJob.getStatus()) && JobStatus.ENABLE.equals(scheduleJob.getStatus())) {
//            quartzManager.updateJob(scheduleJob);
//        } else if (!oldJob.getStatus().equals(scheduleJob.getStatus()) && JobStatus.ENABLE.equals(oldJob.getStatus())) {
//            quartzManager.removeJob(scheduleJob);
//        } else if (!oldJob.getStatus().equals(scheduleJob.getStatus()) && JobStatus.ENABLE.equals(scheduleJob.getStatus())) {
//            quartzManager.addJob(scheduleJob);
//        }
        return "true";
    }

    @RequestMapping(value = "/updateJob2", method = RequestMethod.POST)
    public String updateJob2( Schedule scheduleJob) throws SchedulerException {
        taskService.update(scheduleJob);
        quartzManager.removeJob(scheduleJob);
        if (JobStatus.ENABLE.equals(scheduleJob.getStatus())) {
            quartzManager.addJob(scheduleJob);
        }
//        Schedule oldJob = taskWS.getById(scheduleJob.getId());
//        if (oldJob.getStatus().equals(scheduleJob.getStatus()) && JobStatus.ENABLE.equals(scheduleJob.getStatus())) {
//            quartzManager.updateJob(scheduleJob);
//        } else if (!oldJob.getStatus().equals(scheduleJob.getStatus()) && JobStatus.ENABLE.equals(oldJob.getStatus())) {
//            quartzManager.removeJob(scheduleJob);
//        } else if (!oldJob.getStatus().equals(scheduleJob.getStatus()) && JobStatus.ENABLE.equals(scheduleJob.getStatus())) {
//            quartzManager.addJob(scheduleJob);
//        }
        return "true";
    }

    @RequestMapping(value = "/deleteJob", method = RequestMethod.POST)
    public String deleteJob(@RequestBody Schedule scheduleJob) throws SchedulerException {
        taskService.delete(scheduleJob);
        quartzManager.removeJob(scheduleJob);
        return "";
    }

    @RequestMapping(value = "/deleteJob2", method = RequestMethod.POST)
    public String deleteJob2(Schedule scheduleJob) throws SchedulerException {
        taskService.delete(scheduleJob);
        quartzManager.removeJob(scheduleJob);
        return "";
    }

    @RequestMapping(value = "/executeJob", method = RequestMethod.POST)
    public String executeJob(@RequestBody Schedule scheduleJob) throws SchedulerException {
        quartzManager.executeJob(scheduleJob);
        return "";
    }

    @RequestMapping(value = "/executeJob2", method = RequestMethod.POST)
    public String executeJob2(Schedule scheduleJob) throws SchedulerException {
        quartzManager.executeJob(scheduleJob);
        return "";
    }

}
