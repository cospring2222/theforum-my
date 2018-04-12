package com.theforum.shell;

/**
 * @author Uliana and David
 */
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.SimpleScheduleBuilder.*;



public class QuartzShell {

	public static void start() {
		
        try {
        	Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

    		// define the job and tie it to our HelloJob class
    		JobDetail job = JobBuilder.newJob(DeletePosts.class).withIdentity("job1", "group1").build();

    		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 17 * * ?");
    		// Trigger the job to run now, and then repeat every 40 seconds
//    		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
//    				.withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();

    		
    		  Trigger trigger = TriggerBuilder
    				    .newTrigger()
    				    .withSchedule(scheduleBuilder)
    				    .build();

    		  
    		// Tell quartz to schedule the job using our trigger
    		scheduler.scheduleJob(job, trigger);

    		scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

	}

}
