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


//Processd on start task that  runing shelldured each time period that configured before
public class QuartzShell {

	public static void start() {
		
        try {
        	Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

    		// define the job and tie it to our HelloJob class
    		JobDetail job = JobBuilder.newJob(DeletePosts.class).withIdentity("job1", "group1").build();
    		
    		//period (once a day 17:00)
    		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 17 * * ?");

    		// Trigger the job to run now, and then repeat every scheduleBuilder period (once a day 17:00)	
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
