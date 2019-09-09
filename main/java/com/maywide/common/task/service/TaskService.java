package com.maywide.common.task.service;

import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.maywide.common.task.job.TaskScanJob;

@Service
public class TaskService {
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	public TaskService() {
		
	}
	
	public void start() {
		try {
			logger.info("********************");
			Properties props = new Properties();
			props.load(getClass().getClassLoader().getResourceAsStream("task.properties"));
			
			SchedulerFactory schedulerFactory = new StdSchedulerFactory(props);
			Scheduler scheduler = schedulerFactory.getScheduler();
			
			JobDetail job = JobBuilder.newJob(TaskScanJob.class)
							.withIdentity("TaskScanJob", "TaskScanJobGrp").build();
			
			CronScheduleBuilder scheduleBuilder = 
				CronScheduleBuilder.cronSchedule(props.getProperty("scanDataJobCron"));
			
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("TaskScanTrigger", "TaskScanTriggerGrp")
        				.startNow().withSchedule(scheduleBuilder).build();
			
			scheduler.scheduleJob(job, trigger);
			
			logger.info("开始启动调度系统…………");
		    scheduler.start();

		} catch (Exception e) {
			logger.error("任务调度启动错误", e);
		}
	}
}
