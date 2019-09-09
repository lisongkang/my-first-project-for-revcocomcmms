package com.maywide.biz.remind.job;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.remind.service.BizRemindBatchService;
import com.maywide.core.context.SpringContextHolder;

public class RemindBatchStatJob implements Job
{
	/**
	 * 日志记录器
	 */
	private static final Log log = LogFactory.getLog(RemindBatchStatJob.class);

	public void execute(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException{

		if(log.isInfoEnabled())
		{
			log.info("RemindBatchStatJob Job start!");
		}
		
		try {
			Calendar curCal = Calendar.getInstance();
//    		curCal.add(Calendar.MONTH, -1);
			
			BizRemindBatchService bizRemindBatchService = SpringContextHolder.getBean(BizRemindBatchService.class);
    		bizRemindBatchService.handleRemindBatchEveryday(curCal.getTime());
		} catch (Exception e) {
		}
        
		if(log.isInfoEnabled())
		{
			log.info("RemindBatchStatJob Job finish! ");
		}
	}

}
