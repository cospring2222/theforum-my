package com.theforum.shell;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.SimpleScheduleBuilder.*;

//import static org.quartz.JobBuilder.*;
//import static org.quartz.TriggerBuilder.*;
//import static org.quartz.JobBuilder.*;
//
//import static org.quartz.CronScheduleBuilder.*;
//import static org.quartz.CalendarIntervalScheduleBuilder.*;
//import static org.quartz.TriggerBuilder.*;
//import static org.quartz.DateBuilder.*;

public class QuartzShell {

	public static void start() {
		
        try {
        	Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

    		// define the job and tie it to our HelloJob class
    		JobDetail job = JobBuilder.newJob(DeletePosts.class).withIdentity("job1", "group1").build();

    		// Trigger the job to run now, and then repeat every 40 seconds
    		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
    				.withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();

    		// Tell quartz to schedule the job using our trigger
    		scheduler.scheduleJob(job, trigger);

    		scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

	}

}