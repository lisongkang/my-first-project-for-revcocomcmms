package com.maywide.common.task.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.core.cons.SysConstant;
import com.maywide.core.cons.SysConstant.SysYesNo;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.common.task.entity.CfgTask;
import com.maywide.core.util.DateUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class TaskScanJob implements Job {
	private static final Logger logger = LoggerFactory.getLogger(TaskScanJob.class);

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("#####################");
			_execute(context);
		} catch (Exception e) {
			logger.error("执行扫描任务出现异常", e);
		}
	}

	private void _execute(JobExecutionContext context) throws Exception {
		PersistentService persistentService = 
			(PersistentService) SpringContextHolder.getBean(PersistentService.class);
		logger.info("扫描任务执行");
		CfgTask param = new CfgTask();
		param.setStatus(SysYesNo.YES);
		List<CfgTask> list = persistentService.find(param);

		/*List<CfgTask> list = new ArrayList<CfgTask>();
		CfgTask task2 = new CfgTask();
		task2.setTaskMethod("I");
		//task2.setTaskExpr("0/5 * * * * ?");
		//task2.setTaskExpr("2015-06-26 09:34:00");
		task2.setId(108L);
		
		list.add(task2);*/
		
		if (list.size() == 0) {
			logger.info("扫描到符合要求的任务为零,这次不处理");
			return;
		}
		
		List<JobExecutionContext> runningJobs = context.getScheduler().getCurrentlyExecutingJobs();
		for (JobExecutionContext cxt : runningJobs) {
			if ((cxt.getJobInstance() instanceof TaskJob)) {
				logger.debug("系统中还有未完成的任务,任务ID:" + cxt.getJobDetail().getJobDataMap().getLongValue("taskId") + "," +
						"执行类:" + cxt.getJobDetail().getJobClass().getName());
		    }
		}
		
		for (CfgTask task : list) {
			try {
				if (StringUtils.isBlank(task.getImplClass())) {
					logger.info("任务ID:" + task.getId() + "没有配置业务实现类,无法调度");
					continue;
				}
				String jobName = "job_" + task.getId();
		        String jobGroupName = "TASK_JOB_GROUP";
		        
		        if (isRunning(jobName, jobGroupName, runningJobs)) {
		        	logger.info("job名称:" + jobName + ",任务正在运行，此次不会加载此条数据.等待此任务运行结束,并且下次扫描的时候,才会加载此数据");
		        	continue;
		        }
		        
		        String triggerName = "trigger_" + task.getId();
		        String triggerGroupName = "TASK_TRIGGER_GROUP";
		        
		        JobDetail job = JobBuilder.newJob(TaskJob.class).withIdentity(jobName, jobGroupName).build();
		        Trigger trigger = null;
		        
		        if (task.getTaskMethod().equals(SysConstant.TaskType.CRON)) {//周期
		        	if (StringUtils.isBlank(task.getTaskExpr())) {
		                logger.error("任务ID:" + task.getId() + ",配置的周期执行时间为空");
		                continue;
		            }
		        	CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTaskExpr());
		        	trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
		        				.withSchedule(scheduleBuilder).build();
		        }
		        
		        if (task.getTaskMethod().equals(SysConstant.TaskType.SIMPLE)) {//指定时间
		        	Date startTime = DateUtils.parseTime(task.getTaskExpr());
		        	trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).startAt(startTime).build();
		        }
		        
		        if (task.getTaskMethod().equals(SysConstant.TaskType.IMMEDIATELY)) {//指定时间
		        	trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).startNow().build();
		        }
		        
		        job.getJobDataMap().put("taskId", task.getId());
		        job.getJobDataMap().put("implClass", task.getImplClass());
		        context.getScheduler().scheduleJob(job, trigger);
		        logger.info("加入调度的任务ID：" + task.getId());
			} catch (Exception e) {
				logger.error("任务ID:" + task.getId() + ",无法调度", e);
			}
		}
	}

	private static boolean isRunning(String jobName, String groupName,
			List runningJobs) throws Exception {
		boolean rtn = false;
		for (Iterator iter = runningJobs.iterator(); iter.hasNext();) {
			JobExecutionContext item = (JobExecutionContext) iter.next();
			if ((item.getJobDetail().getKey().getName().equalsIgnoreCase(jobName))
					&& (item.getJobDetail().getKey().getGroup()
							.equalsIgnoreCase(groupName))) {
				rtn = true;
				break;
			}
		}
		return rtn;
	}

	private static void removeJobAndTrigger(String jobName, String groupName,
			JobExecutionContext context) throws Exception {
		JobKey jobKey = JobKey.jobKey(jobName, groupName);
		boolean rtn = context.getScheduler().deleteJob(jobKey);
		if (logger.isDebugEnabled()) {
			if (rtn) {
				logger.debug("删除的job名称:" + jobName);
			} else {
				logger.debug("job名称:" + jobName + "在调度器中没有找到,所以不需要删除");
			}
		}
	}

}
