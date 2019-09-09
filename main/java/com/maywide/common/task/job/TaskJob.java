package com.maywide.common.task.job;

import org.apache.commons.lang3.ClassUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.common.task.interfaces.ITask;

public class TaskJob implements Job {
	private static final Logger logger = LoggerFactory.getLogger(TaskJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			JobDataMap data = context.getJobDetail().getJobDataMap();
			Long taskId = data.getLong("taskId");
			String implClass = data.getString("implClass");
			logger.info("$$$$$$$$$$$$$$$$$$$$：" + taskId);
			//Thread.sleep(20000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Long taskId = 0l;
		String implClass = null;
		String taskResult = null;
		try {
			JobDataMap data = context.getJobDetail().getJobDataMap();
			taskId = data.getLong("taskId");
			implClass = data.getString("implClass");
			Class taskClass = Class.forName(implClass);
			if (!ClassUtils.isAssignable(taskClass, ITask.class)) {
		        throw new JobExecutionException("类:" + implClass + ",没有实现" + ITask.class.getName() + "接口");
		    }
			
			ITask objTask = (ITask)taskClass.newInstance();
			taskResult = objTask.doTask();
			
			logger.info("类:" + implClass + "执行结果：" + taskResult);
		} catch (Exception e) {
			logger.error("执行任务出现异常", e);
		}
	}
}
