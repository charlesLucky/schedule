/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dragon.task;

import com.dragon.util.ApacheHttpClient;
import com.platform.beans.Schedule;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author ban
 */
@DisallowConcurrentExecution
public class QuartzJob implements Job {

    private static final Logger LOG = Logger.getLogger(QuartzJob.class.getName());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Schedule scheduleJob = (Schedule) context.getMergedJobDataMap().get("scheduleJob");
        try {
            String result = ApacheHttpClient.getMethod(scheduleJob.getContent(), null);
            LOG.log(Level.INFO, scheduleJob.getContent() + "---> "+result);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, scheduleJob.getContent() + " access error");
            LOG.log(Level.SEVERE, e.getMessage());
        }

    }

}
